import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Firma } from './firma.model';
import { FirmaPopupService } from './firma-popup.service';
import { FirmaService } from './firma.service';
import { GenelFirmaBilgileri, GenelFirmaBilgileriService } from '../genel-firma-bilgileri';
import { IsyeriBilgileri, IsyeriBilgileriService } from '../isyeri-bilgileri';
import { OrtaklikBilgileri, OrtaklikBilgileriService } from '../ortaklik-bilgileri';
import { FinansalBilgileri, FinansalBilgileriService } from '../finansal-bilgileri';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-firma-dialog',
    templateUrl: './firma-dialog.component.html'
})
export class FirmaDialogComponent implements OnInit {

    firma: Firma;
    isSaving: boolean;

    genelbilgilers: GenelFirmaBilgileri[];

    isyeribilgileris: IsyeriBilgileri[];

    ortaklikbilgileris: OrtaklikBilgileri[];

    finansalbilgileris: FinansalBilgileri[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private firmaService: FirmaService,
        private genelFirmaBilgileriService: GenelFirmaBilgileriService,
        private isyeriBilgileriService: IsyeriBilgileriService,
        private ortaklikBilgileriService: OrtaklikBilgileriService,
        private finansalBilgileriService: FinansalBilgileriService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.genelFirmaBilgileriService
            .query({filter: 'firma-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.firma.genelBilgiler || !this.firma.genelBilgiler.id) {
                    this.genelbilgilers = res.json;
                } else {
                    this.genelFirmaBilgileriService
                        .find(this.firma.genelBilgiler.id)
                        .subscribe((subRes: GenelFirmaBilgileri) => {
                            this.genelbilgilers = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.isyeriBilgileriService
            .query({filter: 'firma-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.firma.isyeriBilgileri || !this.firma.isyeriBilgileri.id) {
                    this.isyeribilgileris = res.json;
                } else {
                    this.isyeriBilgileriService
                        .find(this.firma.isyeriBilgileri.id)
                        .subscribe((subRes: IsyeriBilgileri) => {
                            this.isyeribilgileris = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.ortaklikBilgileriService
            .query({filter: 'firma-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.firma.ortaklikBilgileri || !this.firma.ortaklikBilgileri.id) {
                    this.ortaklikbilgileris = res.json;
                } else {
                    this.ortaklikBilgileriService
                        .find(this.firma.ortaklikBilgileri.id)
                        .subscribe((subRes: OrtaklikBilgileri) => {
                            this.ortaklikbilgileris = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.finansalBilgileriService
            .query({filter: 'firma-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.firma.finansalBilgileri || !this.firma.finansalBilgileri.id) {
                    this.finansalbilgileris = res.json;
                } else {
                    this.finansalBilgileriService
                        .find(this.firma.finansalBilgileri.id)
                        .subscribe((subRes: FinansalBilgileri) => {
                            this.finansalbilgileris = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.firma.id !== undefined) {
            this.subscribeToSaveResponse(
                this.firmaService.update(this.firma));
        } else {
            this.subscribeToSaveResponse(
                this.firmaService.create(this.firma));
        }
    }

    private subscribeToSaveResponse(result: Observable<Firma>) {
        result.subscribe((res: Firma) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Firma) {
        this.eventManager.broadcast({ name: 'firmaListModification', content: 'OK'});
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

    trackGenelFirmaBilgileriById(index: number, item: GenelFirmaBilgileri) {
        return item.id;
    }

    trackIsyeriBilgileriById(index: number, item: IsyeriBilgileri) {
        return item.id;
    }

    trackOrtaklikBilgileriById(index: number, item: OrtaklikBilgileri) {
        return item.id;
    }

    trackFinansalBilgileriById(index: number, item: FinansalBilgileri) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-firma-popup',
    template: ''
})
export class FirmaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private firmaPopupService: FirmaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.firmaPopupService
                    .open(FirmaDialogComponent as Component, params['id']);
            } else {
                this.firmaPopupService
                    .open(FirmaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
