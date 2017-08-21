import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FaaliyetAlani } from './faaliyet-alani.model';
import { FaaliyetAlaniPopupService } from './faaliyet-alani-popup.service';
import { FaaliyetAlaniService } from './faaliyet-alani.service';

@Component({
    selector: 'jhi-faaliyet-alani-dialog',
    templateUrl: './faaliyet-alani-dialog.component.html'
})
export class FaaliyetAlaniDialogComponent implements OnInit {

    faaliyetAlani: FaaliyetAlani;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private faaliyetAlaniService: FaaliyetAlaniService,
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
        if (this.faaliyetAlani.id !== undefined) {
            this.subscribeToSaveResponse(
                this.faaliyetAlaniService.update(this.faaliyetAlani));
        } else {
            this.subscribeToSaveResponse(
                this.faaliyetAlaniService.create(this.faaliyetAlani));
        }
    }

    private subscribeToSaveResponse(result: Observable<FaaliyetAlani>) {
        result.subscribe((res: FaaliyetAlani) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FaaliyetAlani) {
        this.eventManager.broadcast({ name: 'faaliyetAlaniListModification', content: 'OK'});
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
    selector: 'jhi-faaliyet-alani-popup',
    template: ''
})
export class FaaliyetAlaniPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private faaliyetAlaniPopupService: FaaliyetAlaniPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.faaliyetAlaniPopupService
                    .open(FaaliyetAlaniDialogComponent as Component, params['id']);
            } else {
                this.faaliyetAlaniPopupService
                    .open(FaaliyetAlaniDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
