import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BelgeTipi } from './belge-tipi.model';
import { BelgeTipiPopupService } from './belge-tipi-popup.service';
import { BelgeTipiService } from './belge-tipi.service';

@Component({
    selector: 'jhi-belge-tipi-dialog',
    templateUrl: './belge-tipi-dialog.component.html'
})
export class BelgeTipiDialogComponent implements OnInit {

    belgeTipi: BelgeTipi;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private belgeTipiService: BelgeTipiService,
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
        if (this.belgeTipi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.belgeTipiService.update(this.belgeTipi));
        } else {
            this.subscribeToSaveResponse(
                this.belgeTipiService.create(this.belgeTipi));
        }
    }

    private subscribeToSaveResponse(result: Observable<BelgeTipi>) {
        result.subscribe((res: BelgeTipi) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BelgeTipi) {
        this.eventManager.broadcast({ name: 'belgeTipiListModification', content: 'OK'});
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
    selector: 'jhi-belge-tipi-popup',
    template: ''
})
export class BelgeTipiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private belgeTipiPopupService: BelgeTipiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.belgeTipiPopupService
                    .open(BelgeTipiDialogComponent as Component, params['id']);
            } else {
                this.belgeTipiPopupService
                    .open(BelgeTipiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
