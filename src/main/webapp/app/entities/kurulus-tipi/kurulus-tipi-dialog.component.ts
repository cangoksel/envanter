import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { KurulusTipi } from './kurulus-tipi.model';
import { KurulusTipiPopupService } from './kurulus-tipi-popup.service';
import { KurulusTipiService } from './kurulus-tipi.service';

@Component({
    selector: 'jhi-kurulus-tipi-dialog',
    templateUrl: './kurulus-tipi-dialog.component.html'
})
export class KurulusTipiDialogComponent implements OnInit {

    kurulusTipi: KurulusTipi;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private kurulusTipiService: KurulusTipiService,
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
        if (this.kurulusTipi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.kurulusTipiService.update(this.kurulusTipi));
        } else {
            this.subscribeToSaveResponse(
                this.kurulusTipiService.create(this.kurulusTipi));
        }
    }

    private subscribeToSaveResponse(result: Observable<KurulusTipi>) {
        result.subscribe((res: KurulusTipi) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: KurulusTipi) {
        this.eventManager.broadcast({ name: 'kurulusTipiListModification', content: 'OK'});
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
    selector: 'jhi-kurulus-tipi-popup',
    template: ''
})
export class KurulusTipiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kurulusTipiPopupService: KurulusTipiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.kurulusTipiPopupService
                    .open(KurulusTipiDialogComponent as Component, params['id']);
            } else {
                this.kurulusTipiPopupService
                    .open(KurulusTipiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
