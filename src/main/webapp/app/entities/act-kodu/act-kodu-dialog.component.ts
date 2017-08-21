import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ActKodu } from './act-kodu.model';
import { ActKoduPopupService } from './act-kodu-popup.service';
import { ActKoduService } from './act-kodu.service';

@Component({
    selector: 'jhi-act-kodu-dialog',
    templateUrl: './act-kodu-dialog.component.html'
})
export class ActKoduDialogComponent implements OnInit {

    actKodu: ActKodu;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private actKoduService: ActKoduService,
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
        if (this.actKodu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.actKoduService.update(this.actKodu));
        } else {
            this.subscribeToSaveResponse(
                this.actKoduService.create(this.actKodu));
        }
    }

    private subscribeToSaveResponse(result: Observable<ActKodu>) {
        result.subscribe((res: ActKodu) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ActKodu) {
        this.eventManager.broadcast({ name: 'actKoduListModification', content: 'OK'});
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
    selector: 'jhi-act-kodu-popup',
    template: ''
})
export class ActKoduPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private actKoduPopupService: ActKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.actKoduPopupService
                    .open(ActKoduDialogComponent as Component, params['id']);
            } else {
                this.actKoduPopupService
                    .open(ActKoduDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
