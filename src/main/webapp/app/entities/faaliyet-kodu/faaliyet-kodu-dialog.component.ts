import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FaaliyetKodu } from './faaliyet-kodu.model';
import { FaaliyetKoduPopupService } from './faaliyet-kodu-popup.service';
import { FaaliyetKoduService } from './faaliyet-kodu.service';

@Component({
    selector: 'jhi-faaliyet-kodu-dialog',
    templateUrl: './faaliyet-kodu-dialog.component.html'
})
export class FaaliyetKoduDialogComponent implements OnInit {

    faaliyetKodu: FaaliyetKodu;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private faaliyetKoduService: FaaliyetKoduService,
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
        if (this.faaliyetKodu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.faaliyetKoduService.update(this.faaliyetKodu));
        } else {
            this.subscribeToSaveResponse(
                this.faaliyetKoduService.create(this.faaliyetKodu));
        }
    }

    private subscribeToSaveResponse(result: Observable<FaaliyetKodu>) {
        result.subscribe((res: FaaliyetKodu) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FaaliyetKodu) {
        this.eventManager.broadcast({ name: 'faaliyetKoduListModification', content: 'OK'});
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
    selector: 'jhi-faaliyet-kodu-popup',
    template: ''
})
export class FaaliyetKoduPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private faaliyetKoduPopupService: FaaliyetKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.faaliyetKoduPopupService
                    .open(FaaliyetKoduDialogComponent as Component, params['id']);
            } else {
                this.faaliyetKoduPopupService
                    .open(FaaliyetKoduDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
