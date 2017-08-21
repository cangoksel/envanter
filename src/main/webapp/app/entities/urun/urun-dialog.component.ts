import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Urun } from './urun.model';
import { UrunPopupService } from './urun-popup.service';
import { UrunService } from './urun.service';
import { UrunAltKodu, UrunAltKoduService } from '../urun-alt-kodu';
import { ProdkomKodu, ProdkomKoduService } from '../prodkom-kodu';
import { GtipKodu, GtipKoduService } from '../gtip-kodu';
import { MkysKodu, MkysKoduService } from '../mkys-kodu';
import { MedikalTurKodu, MedikalTurKoduService } from '../medikal-tur-kodu';
import { NaceKodu, NaceKoduService } from '../nace-kodu';
import { ActKodu, ActKoduService } from '../act-kodu';
import { TibbiCihazTehlikeSinifi, TibbiCihazTehlikeSinifiService } from '../tibbi-cihaz-tehlike-sinifi';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-urun-dialog',
    templateUrl: './urun-dialog.component.html'
})
export class UrunDialogComponent implements OnInit {

    urun: Urun;
    isSaving: boolean;

    urunaltkodus: UrunAltKodu[];

    prodkomkodus: ProdkomKodu[];

    gtipkodus: GtipKodu[];

    mkyskodus: MkysKodu[];

    medikalturkodus: MedikalTurKodu[];

    nacekodus: NaceKodu[];

    actkodus: ActKodu[];

    tibbicihaztehlikesinifis: TibbiCihazTehlikeSinifi[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private urunService: UrunService,
        private urunAltKoduService: UrunAltKoduService,
        private prodkomKoduService: ProdkomKoduService,
        private gtipKoduService: GtipKoduService,
        private mkysKoduService: MkysKoduService,
        private medikalTurKoduService: MedikalTurKoduService,
        private naceKoduService: NaceKoduService,
        private actKoduService: ActKoduService,
        private tibbiCihazTehlikeSinifiService: TibbiCihazTehlikeSinifiService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.urunAltKoduService
            .query({filter: 'urun-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.urun.urunAltKodu || !this.urun.urunAltKodu.id) {
                    this.urunaltkodus = res.json;
                } else {
                    this.urunAltKoduService
                        .find(this.urun.urunAltKodu.id)
                        .subscribe((subRes: UrunAltKodu) => {
                            this.urunaltkodus = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.prodkomKoduService
            .query({filter: 'urun-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.urun.prodkomKodu || !this.urun.prodkomKodu.id) {
                    this.prodkomkodus = res.json;
                } else {
                    this.prodkomKoduService
                        .find(this.urun.prodkomKodu.id)
                        .subscribe((subRes: ProdkomKodu) => {
                            this.prodkomkodus = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.gtipKoduService
            .query({filter: 'urun-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.urun.gtipKodu || !this.urun.gtipKodu.id) {
                    this.gtipkodus = res.json;
                } else {
                    this.gtipKoduService
                        .find(this.urun.gtipKodu.id)
                        .subscribe((subRes: GtipKodu) => {
                            this.gtipkodus = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.mkysKoduService
            .query({filter: 'urun-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.urun.mkysKodu || !this.urun.mkysKodu.id) {
                    this.mkyskodus = res.json;
                } else {
                    this.mkysKoduService
                        .find(this.urun.mkysKodu.id)
                        .subscribe((subRes: MkysKodu) => {
                            this.mkyskodus = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.medikalTurKoduService
            .query({filter: 'urun-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.urun.medikalTurKodu || !this.urun.medikalTurKodu.id) {
                    this.medikalturkodus = res.json;
                } else {
                    this.medikalTurKoduService
                        .find(this.urun.medikalTurKodu.id)
                        .subscribe((subRes: MedikalTurKodu) => {
                            this.medikalturkodus = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.naceKoduService
            .query({filter: 'urun-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.urun.naceKodu || !this.urun.naceKodu.id) {
                    this.nacekodus = res.json;
                } else {
                    this.naceKoduService
                        .find(this.urun.naceKodu.id)
                        .subscribe((subRes: NaceKodu) => {
                            this.nacekodus = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.actKoduService
            .query({filter: 'urun-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.urun.actKodu || !this.urun.actKodu.id) {
                    this.actkodus = res.json;
                } else {
                    this.actKoduService
                        .find(this.urun.actKodu.id)
                        .subscribe((subRes: ActKodu) => {
                            this.actkodus = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.tibbiCihazTehlikeSinifiService
            .query({filter: 'urun-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.urun.tibbiCihazTehlikeSinifi || !this.urun.tibbiCihazTehlikeSinifi.id) {
                    this.tibbicihaztehlikesinifis = res.json;
                } else {
                    this.tibbiCihazTehlikeSinifiService
                        .find(this.urun.tibbiCihazTehlikeSinifi.id)
                        .subscribe((subRes: TibbiCihazTehlikeSinifi) => {
                            this.tibbicihaztehlikesinifis = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.urun.id !== undefined) {
            this.subscribeToSaveResponse(
                this.urunService.update(this.urun));
        } else {
            this.subscribeToSaveResponse(
                this.urunService.create(this.urun));
        }
    }

    private subscribeToSaveResponse(result: Observable<Urun>) {
        result.subscribe((res: Urun) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Urun) {
        this.eventManager.broadcast({ name: 'urunListModification', content: 'OK'});
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

    trackUrunAltKoduById(index: number, item: UrunAltKodu) {
        return item.id;
    }

    trackProdkomKoduById(index: number, item: ProdkomKodu) {
        return item.id;
    }

    trackGtipKoduById(index: number, item: GtipKodu) {
        return item.id;
    }

    trackMkysKoduById(index: number, item: MkysKodu) {
        return item.id;
    }

    trackMedikalTurKoduById(index: number, item: MedikalTurKodu) {
        return item.id;
    }

    trackNaceKoduById(index: number, item: NaceKodu) {
        return item.id;
    }

    trackActKoduById(index: number, item: ActKodu) {
        return item.id;
    }

    trackTibbiCihazTehlikeSinifiById(index: number, item: TibbiCihazTehlikeSinifi) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-urun-popup',
    template: ''
})
export class UrunPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private urunPopupService: UrunPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.urunPopupService
                    .open(UrunDialogComponent as Component, params['id']);
            } else {
                this.urunPopupService
                    .open(UrunDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
