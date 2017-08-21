import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FinansalBilgileri } from './finansal-bilgileri.model';
import { FinansalBilgileriPopupService } from './finansal-bilgileri-popup.service';
import { FinansalBilgileriService } from './finansal-bilgileri.service';

@Component({
    selector: 'jhi-finansal-bilgileri-dialog',
    templateUrl: './finansal-bilgileri-dialog.component.html'
})
export class FinansalBilgileriDialogComponent implements OnInit {

    finansalBilgileri: FinansalBilgileri;
    isSaving: boolean;
    yilDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private finansalBilgileriService: FinansalBilgileriService,
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
        if (this.finansalBilgileri.id !== undefined) {
            this.subscribeToSaveResponse(
                this.finansalBilgileriService.update(this.finansalBilgileri));
        } else {
            this.subscribeToSaveResponse(
                this.finansalBilgileriService.create(this.finansalBilgileri));
        }
    }

    private subscribeToSaveResponse(result: Observable<FinansalBilgileri>) {
        result.subscribe((res: FinansalBilgileri) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FinansalBilgileri) {
        this.eventManager.broadcast({ name: 'finansalBilgileriListModification', content: 'OK'});
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
    selector: 'jhi-finansal-bilgileri-popup',
    template: ''
})
export class FinansalBilgileriPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private finansalBilgileriPopupService: FinansalBilgileriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.finansalBilgileriPopupService
                    .open(FinansalBilgileriDialogComponent as Component, params['id']);
            } else {
                this.finansalBilgileriPopupService
                    .open(FinansalBilgileriDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
