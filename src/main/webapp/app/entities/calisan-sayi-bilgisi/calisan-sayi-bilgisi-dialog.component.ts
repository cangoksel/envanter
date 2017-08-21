import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CalisanSayiBilgisi } from './calisan-sayi-bilgisi.model';
import { CalisanSayiBilgisiPopupService } from './calisan-sayi-bilgisi-popup.service';
import { CalisanSayiBilgisiService } from './calisan-sayi-bilgisi.service';
import { IsyeriBilgileri, IsyeriBilgileriService } from '../isyeri-bilgileri';
import { CalisanTipi, CalisanTipiService } from '../calisan-tipi';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-calisan-sayi-bilgisi-dialog',
    templateUrl: './calisan-sayi-bilgisi-dialog.component.html'
})
export class CalisanSayiBilgisiDialogComponent implements OnInit {

    calisanSayiBilgisi: CalisanSayiBilgisi;
    isSaving: boolean;

    isyeribilgileris: IsyeriBilgileri[];

    calisantipis: CalisanTipi[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private calisanSayiBilgisiService: CalisanSayiBilgisiService,
        private isyeriBilgileriService: IsyeriBilgileriService,
        private calisanTipiService: CalisanTipiService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.isyeriBilgileriService.query()
            .subscribe((res: ResponseWrapper) => { this.isyeribilgileris = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.calisanTipiService
            .query({filter: 'calisansayibilgisi-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.calisanSayiBilgisi.calisanTipi || !this.calisanSayiBilgisi.calisanTipi.id) {
                    this.calisantipis = res.json;
                } else {
                    this.calisanTipiService
                        .find(this.calisanSayiBilgisi.calisanTipi.id)
                        .subscribe((subRes: CalisanTipi) => {
                            this.calisantipis = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.calisanSayiBilgisi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.calisanSayiBilgisiService.update(this.calisanSayiBilgisi));
        } else {
            this.subscribeToSaveResponse(
                this.calisanSayiBilgisiService.create(this.calisanSayiBilgisi));
        }
    }

    private subscribeToSaveResponse(result: Observable<CalisanSayiBilgisi>) {
        result.subscribe((res: CalisanSayiBilgisi) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CalisanSayiBilgisi) {
        this.eventManager.broadcast({ name: 'calisanSayiBilgisiListModification', content: 'OK'});
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

    trackIsyeriBilgileriById(index: number, item: IsyeriBilgileri) {
        return item.id;
    }

    trackCalisanTipiById(index: number, item: CalisanTipi) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-calisan-sayi-bilgisi-popup',
    template: ''
})
export class CalisanSayiBilgisiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private calisanSayiBilgisiPopupService: CalisanSayiBilgisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.calisanSayiBilgisiPopupService
                    .open(CalisanSayiBilgisiDialogComponent as Component, params['id']);
            } else {
                this.calisanSayiBilgisiPopupService
                    .open(CalisanSayiBilgisiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
