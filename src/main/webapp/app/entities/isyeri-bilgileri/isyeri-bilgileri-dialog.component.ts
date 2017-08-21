import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IsyeriBilgileri } from './isyeri-bilgileri.model';
import { IsyeriBilgileriPopupService } from './isyeri-bilgileri-popup.service';
import { IsyeriBilgileriService } from './isyeri-bilgileri.service';

@Component({
    selector: 'jhi-isyeri-bilgileri-dialog',
    templateUrl: './isyeri-bilgileri-dialog.component.html'
})
export class IsyeriBilgileriDialogComponent implements OnInit {

    isyeriBilgileri: IsyeriBilgileri;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private isyeriBilgileriService: IsyeriBilgileriService,
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
        if (this.isyeriBilgileri.id !== undefined) {
            this.subscribeToSaveResponse(
                this.isyeriBilgileriService.update(this.isyeriBilgileri));
        } else {
            this.subscribeToSaveResponse(
                this.isyeriBilgileriService.create(this.isyeriBilgileri));
        }
    }

    private subscribeToSaveResponse(result: Observable<IsyeriBilgileri>) {
        result.subscribe((res: IsyeriBilgileri) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: IsyeriBilgileri) {
        this.eventManager.broadcast({ name: 'isyeriBilgileriListModification', content: 'OK'});
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
    selector: 'jhi-isyeri-bilgileri-popup',
    template: ''
})
export class IsyeriBilgileriPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private isyeriBilgileriPopupService: IsyeriBilgileriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.isyeriBilgileriPopupService
                    .open(IsyeriBilgileriDialogComponent as Component, params['id']);
            } else {
                this.isyeriBilgileriPopupService
                    .open(IsyeriBilgileriDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
