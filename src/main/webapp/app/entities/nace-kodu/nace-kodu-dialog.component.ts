import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NaceKodu } from './nace-kodu.model';
import { NaceKoduPopupService } from './nace-kodu-popup.service';
import { NaceKoduService } from './nace-kodu.service';

@Component({
    selector: 'jhi-nace-kodu-dialog',
    templateUrl: './nace-kodu-dialog.component.html'
})
export class NaceKoduDialogComponent implements OnInit {

    naceKodu: NaceKodu;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private naceKoduService: NaceKoduService,
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
        if (this.naceKodu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.naceKoduService.update(this.naceKodu));
        } else {
            this.subscribeToSaveResponse(
                this.naceKoduService.create(this.naceKodu));
        }
    }

    private subscribeToSaveResponse(result: Observable<NaceKodu>) {
        result.subscribe((res: NaceKodu) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: NaceKodu) {
        this.eventManager.broadcast({ name: 'naceKoduListModification', content: 'OK'});
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
    selector: 'jhi-nace-kodu-popup',
    template: ''
})
export class NaceKoduPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private naceKoduPopupService: NaceKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.naceKoduPopupService
                    .open(NaceKoduDialogComponent as Component, params['id']);
            } else {
                this.naceKoduPopupService
                    .open(NaceKoduDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
