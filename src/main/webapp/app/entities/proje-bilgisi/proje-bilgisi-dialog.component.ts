import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProjeBilgisi } from './proje-bilgisi.model';
import { ProjeBilgisiPopupService } from './proje-bilgisi-popup.service';
import { ProjeBilgisiService } from './proje-bilgisi.service';
import { Firma, FirmaService } from '../firma';
import { Kurum, KurumService } from '../kurum';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-proje-bilgisi-dialog',
    templateUrl: './proje-bilgisi-dialog.component.html'
})
export class ProjeBilgisiDialogComponent implements OnInit {

    projeBilgisi: ProjeBilgisi;
    isSaving: boolean;

    firmas: Firma[];

    destekverenkurums: Kurum[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private projeBilgisiService: ProjeBilgisiService,
        private firmaService: FirmaService,
        private kurumService: KurumService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.firmaService.query()
            .subscribe((res: ResponseWrapper) => { this.firmas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.kurumService
            .query({filter: 'projebilgisi-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.projeBilgisi.destekVerenKurum || !this.projeBilgisi.destekVerenKurum.id) {
                    this.destekverenkurums = res.json;
                } else {
                    this.kurumService
                        .find(this.projeBilgisi.destekVerenKurum.id)
                        .subscribe((subRes: Kurum) => {
                            this.destekverenkurums = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.projeBilgisi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.projeBilgisiService.update(this.projeBilgisi));
        } else {
            this.subscribeToSaveResponse(
                this.projeBilgisiService.create(this.projeBilgisi));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProjeBilgisi>) {
        result.subscribe((res: ProjeBilgisi) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ProjeBilgisi) {
        this.eventManager.broadcast({ name: 'projeBilgisiListModification', content: 'OK'});
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

    trackFirmaById(index: number, item: Firma) {
        return item.id;
    }

    trackKurumById(index: number, item: Kurum) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-proje-bilgisi-popup',
    template: ''
})
export class ProjeBilgisiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projeBilgisiPopupService: ProjeBilgisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.projeBilgisiPopupService
                    .open(ProjeBilgisiDialogComponent as Component, params['id']);
            } else {
                this.projeBilgisiPopupService
                    .open(ProjeBilgisiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
