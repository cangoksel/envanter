import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GtipKodu } from './gtip-kodu.model';
import { GtipKoduPopupService } from './gtip-kodu-popup.service';
import { GtipKoduService } from './gtip-kodu.service';

@Component({
    selector: 'jhi-gtip-kodu-dialog',
    templateUrl: './gtip-kodu-dialog.component.html'
})
export class GtipKoduDialogComponent implements OnInit {

    gtipKodu: GtipKodu;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private gtipKoduService: GtipKoduService,
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
        if (this.gtipKodu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.gtipKoduService.update(this.gtipKodu));
        } else {
            this.subscribeToSaveResponse(
                this.gtipKoduService.create(this.gtipKodu));
        }
    }

    private subscribeToSaveResponse(result: Observable<GtipKodu>) {
        result.subscribe((res: GtipKodu) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: GtipKodu) {
        this.eventManager.broadcast({ name: 'gtipKoduListModification', content: 'OK'});
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
    selector: 'jhi-gtip-kodu-popup',
    template: ''
})
export class GtipKoduPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gtipKoduPopupService: GtipKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.gtipKoduPopupService
                    .open(GtipKoduDialogComponent as Component, params['id']);
            } else {
                this.gtipKoduPopupService
                    .open(GtipKoduDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
