import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GenelFirmaBilgileri } from './genel-firma-bilgileri.model';
import { GenelFirmaBilgileriPopupService } from './genel-firma-bilgileri-popup.service';
import { GenelFirmaBilgileriService } from './genel-firma-bilgileri.service';
import { Il, IlService } from '../il';
import { Ulke, UlkeService } from '../ulke';
import { Kisi, KisiService } from '../kisi';
import { FaaliyetAlani, FaaliyetAlaniService } from '../faaliyet-alani';
import { UrunGrubu, UrunGrubuService } from '../urun-grubu';
import { FaaliyetKodu, FaaliyetKoduService } from '../faaliyet-kodu';
import { Adres, AdresService } from '../adres';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-genel-firma-bilgileri-dialog',
    templateUrl: './genel-firma-bilgileri-dialog.component.html'
})
export class GenelFirmaBilgileriDialogComponent implements OnInit {

    genelFirmaBilgileri: GenelFirmaBilgileri;
    isSaving: boolean;

    ils: Il[];

    ulkes: Ulke[];

    kaydiolusturankisis: Kisi[];

    yetkilikisis: Kisi[];

    faaliyetalanis: FaaliyetAlani[];

    urungrubus: UrunGrubu[];

    faaliyetkodus: FaaliyetKodu[];

    adres: Adres[];
    kurulusTarihiDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private genelFirmaBilgileriService: GenelFirmaBilgileriService,
        private ilService: IlService,
        private ulkeService: UlkeService,
        private kisiService: KisiService,
        private faaliyetAlaniService: FaaliyetAlaniService,
        private urunGrubuService: UrunGrubuService,
        private faaliyetKoduService: FaaliyetKoduService,
        private adresService: AdresService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.ilService
            .query({filter: 'genelfirmabilgileri-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.genelFirmaBilgileri.il || !this.genelFirmaBilgileri.il.id) {
                    this.ils = res.json;
                } else {
                    this.ilService
                        .find(this.genelFirmaBilgileri.il.id)
                        .subscribe((subRes: Il) => {
                            this.ils = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.ulkeService
            .query({filter: 'genelfirmabilgileri-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.genelFirmaBilgileri.ulke || !this.genelFirmaBilgileri.ulke.id) {
                    this.ulkes = res.json;
                } else {
                    this.ulkeService
                        .find(this.genelFirmaBilgileri.ulke.id)
                        .subscribe((subRes: Ulke) => {
                            this.ulkes = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.kisiService
            .query({filter: 'genelfirmabilgileri-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.genelFirmaBilgileri.kaydiOlusturanKisi || !this.genelFirmaBilgileri.kaydiOlusturanKisi.id) {
                    this.kaydiolusturankisis = res.json;
                } else {
                    this.kisiService
                        .find(this.genelFirmaBilgileri.kaydiOlusturanKisi.id)
                        .subscribe((subRes: Kisi) => {
                            this.kaydiolusturankisis = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.kisiService
            .query({filter: 'genelfirmabilgileri-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.genelFirmaBilgileri.yetkiliKisi || !this.genelFirmaBilgileri.yetkiliKisi.id) {
                    this.yetkilikisis = res.json;
                } else {
                    this.kisiService
                        .find(this.genelFirmaBilgileri.yetkiliKisi.id)
                        .subscribe((subRes: Kisi) => {
                            this.yetkilikisis = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.faaliyetAlaniService
            .query({filter: 'genelfirmabilgileri-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.genelFirmaBilgileri.faaliyetAlani || !this.genelFirmaBilgileri.faaliyetAlani.id) {
                    this.faaliyetalanis = res.json;
                } else {
                    this.faaliyetAlaniService
                        .find(this.genelFirmaBilgileri.faaliyetAlani.id)
                        .subscribe((subRes: FaaliyetAlani) => {
                            this.faaliyetalanis = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.urunGrubuService
            .query({filter: 'genelfirmabilgileri-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.genelFirmaBilgileri.urunGrubu || !this.genelFirmaBilgileri.urunGrubu.id) {
                    this.urungrubus = res.json;
                } else {
                    this.urunGrubuService
                        .find(this.genelFirmaBilgileri.urunGrubu.id)
                        .subscribe((subRes: UrunGrubu) => {
                            this.urungrubus = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.faaliyetKoduService
            .query({filter: 'genelfirmabilgileri-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.genelFirmaBilgileri.faaliyetKodu || !this.genelFirmaBilgileri.faaliyetKodu.id) {
                    this.faaliyetkodus = res.json;
                } else {
                    this.faaliyetKoduService
                        .find(this.genelFirmaBilgileri.faaliyetKodu.id)
                        .subscribe((subRes: FaaliyetKodu) => {
                            this.faaliyetkodus = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.adresService
            .query({filter: 'genelfirmabilgileri-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.genelFirmaBilgileri.adres || !this.genelFirmaBilgileri.adres.id) {
                    this.adres = res.json;
                } else {
                    this.adresService
                        .find(this.genelFirmaBilgileri.adres.id)
                        .subscribe((subRes: Adres) => {
                            this.adres = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.genelFirmaBilgileri.id !== undefined) {
            this.subscribeToSaveResponse(
                this.genelFirmaBilgileriService.update(this.genelFirmaBilgileri));
        } else {
            this.subscribeToSaveResponse(
                this.genelFirmaBilgileriService.create(this.genelFirmaBilgileri));
        }
    }

    private subscribeToSaveResponse(result: Observable<GenelFirmaBilgileri>) {
        result.subscribe((res: GenelFirmaBilgileri) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: GenelFirmaBilgileri) {
        this.eventManager.broadcast({ name: 'genelFirmaBilgileriListModification', content: 'OK'});
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

    trackIlById(index: number, item: Il) {
        return item.id;
    }

    trackUlkeById(index: number, item: Ulke) {
        return item.id;
    }

    trackKisiById(index: number, item: Kisi) {
        return item.id;
    }

    trackFaaliyetAlaniById(index: number, item: FaaliyetAlani) {
        return item.id;
    }

    trackUrunGrubuById(index: number, item: UrunGrubu) {
        return item.id;
    }

    trackFaaliyetKoduById(index: number, item: FaaliyetKodu) {
        return item.id;
    }

    trackAdresById(index: number, item: Adres) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-genel-firma-bilgileri-popup',
    template: ''
})
export class GenelFirmaBilgileriPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private genelFirmaBilgileriPopupService: GenelFirmaBilgileriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.genelFirmaBilgileriPopupService
                    .open(GenelFirmaBilgileriDialogComponent as Component, params['id']);
            } else {
                this.genelFirmaBilgileriPopupService
                    .open(GenelFirmaBilgileriDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
