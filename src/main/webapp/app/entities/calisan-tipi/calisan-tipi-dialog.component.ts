import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CalisanTipi } from './calisan-tipi.model';
import { CalisanTipiPopupService } from './calisan-tipi-popup.service';
import { CalisanTipiService } from './calisan-tipi.service';

@Component({
    selector: 'jhi-calisan-tipi-dialog',
    templateUrl: './calisan-tipi-dialog.component.html'
})
export class CalisanTipiDialogComponent implements OnInit {

    calisanTipi: CalisanTipi;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private calisanTipiService: CalisanTipiService,
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
        if (this.calisanTipi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.calisanTipiService.update(this.calisanTipi));
        } else {
            this.subscribeToSaveResponse(
                this.calisanTipiService.create(this.calisanTipi));
        }
    }

    private subscribeToSaveResponse(result: Observable<CalisanTipi>) {
        result.subscribe((res: CalisanTipi) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CalisanTipi) {
        this.eventManager.broadcast({ name: 'calisanTipiListModification', content: 'OK'});
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
    selector: 'jhi-calisan-tipi-popup',
    template: ''
})
export class CalisanTipiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private calisanTipiPopupService: CalisanTipiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.calisanTipiPopupService
                    .open(CalisanTipiDialogComponent as Component, params['id']);
            } else {
                this.calisanTipiPopupService
                    .open(CalisanTipiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
