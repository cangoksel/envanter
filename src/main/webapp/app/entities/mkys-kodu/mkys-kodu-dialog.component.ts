import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MkysKodu } from './mkys-kodu.model';
import { MkysKoduPopupService } from './mkys-kodu-popup.service';
import { MkysKoduService } from './mkys-kodu.service';

@Component({
    selector: 'jhi-mkys-kodu-dialog',
    templateUrl: './mkys-kodu-dialog.component.html'
})
export class MkysKoduDialogComponent implements OnInit {

    mkysKodu: MkysKodu;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private mkysKoduService: MkysKoduService,
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
        if (this.mkysKodu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mkysKoduService.update(this.mkysKodu));
        } else {
            this.subscribeToSaveResponse(
                this.mkysKoduService.create(this.mkysKodu));
        }
    }

    private subscribeToSaveResponse(result: Observable<MkysKodu>) {
        result.subscribe((res: MkysKodu) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MkysKodu) {
        this.eventManager.broadcast({ name: 'mkysKoduListModification', content: 'OK'});
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
    selector: 'jhi-mkys-kodu-popup',
    template: ''
})
export class MkysKoduPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mkysKoduPopupService: MkysKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mkysKoduPopupService
                    .open(MkysKoduDialogComponent as Component, params['id']);
            } else {
                this.mkysKoduPopupService
                    .open(MkysKoduDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
