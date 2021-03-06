import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ulke } from './ulke.model';
import { UlkePopupService } from './ulke-popup.service';
import { UlkeService } from './ulke.service';

@Component({
    selector: 'jhi-ulke-dialog',
    templateUrl: './ulke-dialog.component.html'
})
export class UlkeDialogComponent implements OnInit {

    ulke: Ulke;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private ulkeService: UlkeService,
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
        if (this.ulke.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ulkeService.update(this.ulke));
        } else {
            this.subscribeToSaveResponse(
                this.ulkeService.create(this.ulke));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ulke>) {
        result.subscribe((res: Ulke) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Ulke) {
        this.eventManager.broadcast({ name: 'ulkeListModification', content: 'OK'});
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
    selector: 'jhi-ulke-popup',
    template: ''
})
export class UlkePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ulkePopupService: UlkePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ulkePopupService
                    .open(UlkeDialogComponent as Component, params['id']);
            } else {
                this.ulkePopupService
                    .open(UlkeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
