import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TibbiCihazTehlikeSinifi } from './tibbi-cihaz-tehlike-sinifi.model';
import { TibbiCihazTehlikeSinifiPopupService } from './tibbi-cihaz-tehlike-sinifi-popup.service';
import { TibbiCihazTehlikeSinifiService } from './tibbi-cihaz-tehlike-sinifi.service';

@Component({
    selector: 'jhi-tibbi-cihaz-tehlike-sinifi-dialog',
    templateUrl: './tibbi-cihaz-tehlike-sinifi-dialog.component.html'
})
export class TibbiCihazTehlikeSinifiDialogComponent implements OnInit {

    tibbiCihazTehlikeSinifi: TibbiCihazTehlikeSinifi;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private tibbiCihazTehlikeSinifiService: TibbiCihazTehlikeSinifiService,
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
        if (this.tibbiCihazTehlikeSinifi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tibbiCihazTehlikeSinifiService.update(this.tibbiCihazTehlikeSinifi));
        } else {
            this.subscribeToSaveResponse(
                this.tibbiCihazTehlikeSinifiService.create(this.tibbiCihazTehlikeSinifi));
        }
    }

    private subscribeToSaveResponse(result: Observable<TibbiCihazTehlikeSinifi>) {
        result.subscribe((res: TibbiCihazTehlikeSinifi) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TibbiCihazTehlikeSinifi) {
        this.eventManager.broadcast({ name: 'tibbiCihazTehlikeSinifiListModification', content: 'OK'});
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
    selector: 'jhi-tibbi-cihaz-tehlike-sinifi-popup',
    template: ''
})
export class TibbiCihazTehlikeSinifiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tibbiCihazTehlikeSinifiPopupService: TibbiCihazTehlikeSinifiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tibbiCihazTehlikeSinifiPopupService
                    .open(TibbiCihazTehlikeSinifiDialogComponent as Component, params['id']);
            } else {
                this.tibbiCihazTehlikeSinifiPopupService
                    .open(TibbiCihazTehlikeSinifiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
