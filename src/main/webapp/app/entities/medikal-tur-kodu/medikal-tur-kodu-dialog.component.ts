import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MedikalTurKodu } from './medikal-tur-kodu.model';
import { MedikalTurKoduPopupService } from './medikal-tur-kodu-popup.service';
import { MedikalTurKoduService } from './medikal-tur-kodu.service';

@Component({
    selector: 'jhi-medikal-tur-kodu-dialog',
    templateUrl: './medikal-tur-kodu-dialog.component.html'
})
export class MedikalTurKoduDialogComponent implements OnInit {

    medikalTurKodu: MedikalTurKodu;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private medikalTurKoduService: MedikalTurKoduService,
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
        if (this.medikalTurKodu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.medikalTurKoduService.update(this.medikalTurKodu));
        } else {
            this.subscribeToSaveResponse(
                this.medikalTurKoduService.create(this.medikalTurKodu));
        }
    }

    private subscribeToSaveResponse(result: Observable<MedikalTurKodu>) {
        result.subscribe((res: MedikalTurKodu) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MedikalTurKodu) {
        this.eventManager.broadcast({ name: 'medikalTurKoduListModification', content: 'OK'});
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
    selector: 'jhi-medikal-tur-kodu-popup',
    template: ''
})
export class MedikalTurKoduPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private medikalTurKoduPopupService: MedikalTurKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.medikalTurKoduPopupService
                    .open(MedikalTurKoduDialogComponent as Component, params['id']);
            } else {
                this.medikalTurKoduPopupService
                    .open(MedikalTurKoduDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
