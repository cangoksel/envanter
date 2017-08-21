import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UrunGrubu } from './urun-grubu.model';
import { UrunGrubuPopupService } from './urun-grubu-popup.service';
import { UrunGrubuService } from './urun-grubu.service';

@Component({
    selector: 'jhi-urun-grubu-dialog',
    templateUrl: './urun-grubu-dialog.component.html'
})
export class UrunGrubuDialogComponent implements OnInit {

    urunGrubu: UrunGrubu;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private urunGrubuService: UrunGrubuService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.urunGrubu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.urunGrubuService.update(this.urunGrubu));
        } else {
            this.subscribeToSaveResponse(
                this.urunGrubuService.create(this.urunGrubu));
        }
    }

    private subscribeToSaveResponse(result: Observable<UrunGrubu>) {
        result.subscribe((res: UrunGrubu) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: UrunGrubu) {
        this.eventManager.broadcast({ name: 'urunGrubuListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-urun-grubu-popup',
    template: ''
})
export class UrunGrubuPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private urunGrubuPopupService: UrunGrubuPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.urunGrubuPopupService
                    .open(UrunGrubuDialogComponent as Component, params['id']);
            } else {
                this.urunGrubuPopupService
                    .open(UrunGrubuDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
