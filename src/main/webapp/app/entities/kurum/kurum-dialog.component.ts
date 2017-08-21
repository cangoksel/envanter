import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Kurum } from './kurum.model';
import { KurumPopupService } from './kurum-popup.service';
import { KurumService } from './kurum.service';

@Component({
    selector: 'jhi-kurum-dialog',
    templateUrl: './kurum-dialog.component.html'
})
export class KurumDialogComponent implements OnInit {

    kurum: Kurum;
    isSaving: boolean;
    yilDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private kurumService: KurumService,
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
        if (this.kurum.id !== undefined) {
            this.subscribeToSaveResponse(
                this.kurumService.update(this.kurum));
        } else {
            this.subscribeToSaveResponse(
                this.kurumService.create(this.kurum));
        }
    }

    private subscribeToSaveResponse(result: Observable<Kurum>) {
        result.subscribe((res: Kurum) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Kurum) {
        this.eventManager.broadcast({ name: 'kurumListModification', content: 'OK'});
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
    selector: 'jhi-kurum-popup',
    template: ''
})
export class KurumPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kurumPopupService: KurumPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.kurumPopupService
                    .open(KurumDialogComponent as Component, params['id']);
            } else {
                this.kurumPopupService
                    .open(KurumDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
