import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProdkomKodu } from './prodkom-kodu.model';
import { ProdkomKoduPopupService } from './prodkom-kodu-popup.service';
import { ProdkomKoduService } from './prodkom-kodu.service';

@Component({
    selector: 'jhi-prodkom-kodu-dialog',
    templateUrl: './prodkom-kodu-dialog.component.html'
})
export class ProdkomKoduDialogComponent implements OnInit {

    prodkomKodu: ProdkomKodu;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private prodkomKoduService: ProdkomKoduService,
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
        if (this.prodkomKodu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.prodkomKoduService.update(this.prodkomKodu));
        } else {
            this.subscribeToSaveResponse(
                this.prodkomKoduService.create(this.prodkomKodu));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProdkomKodu>) {
        result.subscribe((res: ProdkomKodu) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ProdkomKodu) {
        this.eventManager.broadcast({ name: 'prodkomKoduListModification', content: 'OK'});
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
    selector: 'jhi-prodkom-kodu-popup',
    template: ''
})
export class ProdkomKoduPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prodkomKoduPopupService: ProdkomKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.prodkomKoduPopupService
                    .open(ProdkomKoduDialogComponent as Component, params['id']);
            } else {
                this.prodkomKoduPopupService
                    .open(ProdkomKoduDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
