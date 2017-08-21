import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UretimBilgisi } from './uretim-bilgisi.model';
import { UretimBilgisiPopupService } from './uretim-bilgisi-popup.service';
import { UretimBilgisiService } from './uretim-bilgisi.service';
import { Firma, FirmaService } from '../firma';
import { Urun, UrunService } from '../urun';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-uretim-bilgisi-dialog',
    templateUrl: './uretim-bilgisi-dialog.component.html'
})
export class UretimBilgisiDialogComponent implements OnInit {

    uretimBilgisi: UretimBilgisi;
    isSaving: boolean;

    firmas: Firma[];

    uruns: Urun[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private uretimBilgisiService: UretimBilgisiService,
        private firmaService: FirmaService,
        private urunService: UrunService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.firmaService.query()
            .subscribe((res: ResponseWrapper) => { this.firmas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.urunService.query()
            .subscribe((res: ResponseWrapper) => { this.uruns = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.uretimBilgisi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.uretimBilgisiService.update(this.uretimBilgisi));
        } else {
            this.subscribeToSaveResponse(
                this.uretimBilgisiService.create(this.uretimBilgisi));
        }
    }

    private subscribeToSaveResponse(result: Observable<UretimBilgisi>) {
        result.subscribe((res: UretimBilgisi) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: UretimBilgisi) {
        this.eventManager.broadcast({ name: 'uretimBilgisiListModification', content: 'OK'});
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

    trackUrunById(index: number, item: Urun) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-uretim-bilgisi-popup',
    template: ''
})
export class UretimBilgisiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private uretimBilgisiPopupService: UretimBilgisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.uretimBilgisiPopupService
                    .open(UretimBilgisiDialogComponent as Component, params['id']);
            } else {
                this.uretimBilgisiPopupService
                    .open(UretimBilgisiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
