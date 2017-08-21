import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UrunGrubuKodu } from './urun-grubu-kodu.model';
import { UrunGrubuKoduPopupService } from './urun-grubu-kodu-popup.service';
import { UrunGrubuKoduService } from './urun-grubu-kodu.service';

@Component({
    selector: 'jhi-urun-grubu-kodu-dialog',
    templateUrl: './urun-grubu-kodu-dialog.component.html'
})
export class UrunGrubuKoduDialogComponent implements OnInit {

    urunGrubuKodu: UrunGrubuKodu;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private urunGrubuKoduService: UrunGrubuKoduService,
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
        if (this.urunGrubuKodu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.urunGrubuKoduService.update(this.urunGrubuKodu));
        } else {
            this.subscribeToSaveResponse(
                this.urunGrubuKoduService.create(this.urunGrubuKodu));
        }
    }

    private subscribeToSaveResponse(result: Observable<UrunGrubuKodu>) {
        result.subscribe((res: UrunGrubuKodu) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: UrunGrubuKodu) {
        this.eventManager.broadcast({ name: 'urunGrubuKoduListModification', content: 'OK'});
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
    selector: 'jhi-urun-grubu-kodu-popup',
    template: ''
})
export class UrunGrubuKoduPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private urunGrubuKoduPopupService: UrunGrubuKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.urunGrubuKoduPopupService
                    .open(UrunGrubuKoduDialogComponent as Component, params['id']);
            } else {
                this.urunGrubuKoduPopupService
                    .open(UrunGrubuKoduDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
