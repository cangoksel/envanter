import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Kisi } from './kisi.model';
import { KisiPopupService } from './kisi-popup.service';
import { KisiService } from './kisi.service';

@Component({
    selector: 'jhi-kisi-dialog',
    templateUrl: './kisi-dialog.component.html'
})
export class KisiDialogComponent implements OnInit {

    kisi: Kisi;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private kisiService: KisiService,
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
        if (this.kisi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.kisiService.update(this.kisi));
        } else {
            this.subscribeToSaveResponse(
                this.kisiService.create(this.kisi));
        }
    }

    private subscribeToSaveResponse(result: Observable<Kisi>) {
        result.subscribe((res: Kisi) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Kisi) {
        this.eventManager.broadcast({ name: 'kisiListModification', content: 'OK'});
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
    selector: 'jhi-kisi-popup',
    template: ''
})
export class KisiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kisiPopupService: KisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.kisiPopupService
                    .open(KisiDialogComponent as Component, params['id']);
            } else {
                this.kisiPopupService
                    .open(KisiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
