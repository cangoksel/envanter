import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Bolge } from './bolge.model';
import { BolgePopupService } from './bolge-popup.service';
import { BolgeService } from './bolge.service';

@Component({
    selector: 'jhi-bolge-dialog',
    templateUrl: './bolge-dialog.component.html'
})
export class BolgeDialogComponent implements OnInit {

    bolge: Bolge;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private bolgeService: BolgeService,
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
        if (this.bolge.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bolgeService.update(this.bolge));
        } else {
            this.subscribeToSaveResponse(
                this.bolgeService.create(this.bolge));
        }
    }

    private subscribeToSaveResponse(result: Observable<Bolge>) {
        result.subscribe((res: Bolge) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Bolge) {
        this.eventManager.broadcast({ name: 'bolgeListModification', content: 'OK'});
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
    selector: 'jhi-bolge-popup',
    template: ''
})
export class BolgePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bolgePopupService: BolgePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bolgePopupService
                    .open(BolgeDialogComponent as Component, params['id']);
            } else {
                this.bolgePopupService
                    .open(BolgeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
