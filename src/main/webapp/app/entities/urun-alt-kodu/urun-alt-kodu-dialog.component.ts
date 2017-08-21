import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UrunAltKodu } from './urun-alt-kodu.model';
import { UrunAltKoduPopupService } from './urun-alt-kodu-popup.service';
import { UrunAltKoduService } from './urun-alt-kodu.service';
import { UrunKodu, UrunKoduService } from '../urun-kodu';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-urun-alt-kodu-dialog',
    templateUrl: './urun-alt-kodu-dialog.component.html'
})
export class UrunAltKoduDialogComponent implements OnInit {

    urunAltKodu: UrunAltKodu;
    isSaving: boolean;

    urunkodus: UrunKodu[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private urunAltKoduService: UrunAltKoduService,
        private urunKoduService: UrunKoduService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.urunKoduService
            .query({filter: 'urunaltkodu-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.urunAltKodu.urunKodu || !this.urunAltKodu.urunKodu.id) {
                    this.urunkodus = res.json;
                } else {
                    this.urunKoduService
                        .find(this.urunAltKodu.urunKodu.id)
                        .subscribe((subRes: UrunKodu) => {
                            this.urunkodus = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.urunAltKodu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.urunAltKoduService.update(this.urunAltKodu));
        } else {
            this.subscribeToSaveResponse(
                this.urunAltKoduService.create(this.urunAltKodu));
        }
    }

    private subscribeToSaveResponse(result: Observable<UrunAltKodu>) {
        result.subscribe((res: UrunAltKodu) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: UrunAltKodu) {
        this.eventManager.broadcast({ name: 'urunAltKoduListModification', content: 'OK'});
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

    trackUrunKoduById(index: number, item: UrunKodu) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-urun-alt-kodu-popup',
    template: ''
})
export class UrunAltKoduPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private urunAltKoduPopupService: UrunAltKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.urunAltKoduPopupService
                    .open(UrunAltKoduDialogComponent as Component, params['id']);
            } else {
                this.urunAltKoduPopupService
                    .open(UrunAltKoduDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
