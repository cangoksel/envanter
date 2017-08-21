import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OrtaklikBilgileri } from './ortaklik-bilgileri.model';
import { OrtaklikBilgileriPopupService } from './ortaklik-bilgileri-popup.service';
import { OrtaklikBilgileriService } from './ortaklik-bilgileri.service';

@Component({
    selector: 'jhi-ortaklik-bilgileri-dialog',
    templateUrl: './ortaklik-bilgileri-dialog.component.html'
})
export class OrtaklikBilgileriDialogComponent implements OnInit {

    ortaklikBilgileri: OrtaklikBilgileri;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private ortaklikBilgileriService: OrtaklikBilgileriService,
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
        if (this.ortaklikBilgileri.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ortaklikBilgileriService.update(this.ortaklikBilgileri));
        } else {
            this.subscribeToSaveResponse(
                this.ortaklikBilgileriService.create(this.ortaklikBilgileri));
        }
    }

    private subscribeToSaveResponse(result: Observable<OrtaklikBilgileri>) {
        result.subscribe((res: OrtaklikBilgileri) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: OrtaklikBilgileri) {
        this.eventManager.broadcast({ name: 'ortaklikBilgileriListModification', content: 'OK'});
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
    selector: 'jhi-ortaklik-bilgileri-popup',
    template: ''
})
export class OrtaklikBilgileriPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ortaklikBilgileriPopupService: OrtaklikBilgileriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ortaklikBilgileriPopupService
                    .open(OrtaklikBilgileriDialogComponent as Component, params['id']);
            } else {
                this.ortaklikBilgileriPopupService
                    .open(OrtaklikBilgileriDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
