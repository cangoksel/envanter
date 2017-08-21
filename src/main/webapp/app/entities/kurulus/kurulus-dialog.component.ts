import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Kurulus } from './kurulus.model';
import { KurulusPopupService } from './kurulus-popup.service';
import { KurulusService } from './kurulus.service';
import { IsyeriBilgileri, IsyeriBilgileriService } from '../isyeri-bilgileri';
import { KurulusTipi, KurulusTipiService } from '../kurulus-tipi';
import { FaaliyetAlani, FaaliyetAlaniService } from '../faaliyet-alani';
import { UrunGrubu, UrunGrubuService } from '../urun-grubu';
import { Kisi, KisiService } from '../kisi';
import { Adres, AdresService } from '../adres';
import { Il, IlService } from '../il';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-kurulus-dialog',
    templateUrl: './kurulus-dialog.component.html'
})
export class KurulusDialogComponent implements OnInit {

    kurulus: Kurulus;
    isSaving: boolean;

    isyeribilgileris: IsyeriBilgileri[];

    kurulustipis: KurulusTipi[];

    faaliyetalanis: FaaliyetAlani[];

    urungrubus: UrunGrubu[];

    yetkilikisis: Kisi[];

    adres: Adres[];

    bulunduguils: Il[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private kurulusService: KurulusService,
        private isyeriBilgileriService: IsyeriBilgileriService,
        private kurulusTipiService: KurulusTipiService,
        private faaliyetAlaniService: FaaliyetAlaniService,
        private urunGrubuService: UrunGrubuService,
        private kisiService: KisiService,
        private adresService: AdresService,
        private ilService: IlService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.isyeriBilgileriService.query()
            .subscribe((res: ResponseWrapper) => { this.isyeribilgileris = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.kurulusTipiService
            .query({filter: 'kurulus-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.kurulus.kurulusTipi || !this.kurulus.kurulusTipi.id) {
                    this.kurulustipis = res.json;
                } else {
                    this.kurulusTipiService
                        .find(this.kurulus.kurulusTipi.id)
                        .subscribe((subRes: KurulusTipi) => {
                            this.kurulustipis = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.faaliyetAlaniService
            .query({filter: 'kurulus-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.kurulus.faaliyetAlani || !this.kurulus.faaliyetAlani.id) {
                    this.faaliyetalanis = res.json;
                } else {
                    this.faaliyetAlaniService
                        .find(this.kurulus.faaliyetAlani.id)
                        .subscribe((subRes: FaaliyetAlani) => {
                            this.faaliyetalanis = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.urunGrubuService
            .query({filter: 'kurulus-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.kurulus.urunGrubu || !this.kurulus.urunGrubu.id) {
                    this.urungrubus = res.json;
                } else {
                    this.urunGrubuService
                        .find(this.kurulus.urunGrubu.id)
                        .subscribe((subRes: UrunGrubu) => {
                            this.urungrubus = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.kisiService
            .query({filter: 'kurulus-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.kurulus.yetkiliKisi || !this.kurulus.yetkiliKisi.id) {
                    this.yetkilikisis = res.json;
                } else {
                    this.kisiService
                        .find(this.kurulus.yetkiliKisi.id)
                        .subscribe((subRes: Kisi) => {
                            this.yetkilikisis = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.adresService
            .query({filter: 'kurulus-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.kurulus.adres || !this.kurulus.adres.id) {
                    this.adres = res.json;
                } else {
                    this.adresService
                        .find(this.kurulus.adres.id)
                        .subscribe((subRes: Adres) => {
                            this.adres = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.ilService
            .query({filter: 'kurulus-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.kurulus.bulunduguIl || !this.kurulus.bulunduguIl.id) {
                    this.bulunduguils = res.json;
                } else {
                    this.ilService
                        .find(this.kurulus.bulunduguIl.id)
                        .subscribe((subRes: Il) => {
                            this.bulunduguils = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.kurulus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.kurulusService.update(this.kurulus));
        } else {
            this.subscribeToSaveResponse(
                this.kurulusService.create(this.kurulus));
        }
    }

    private subscribeToSaveResponse(result: Observable<Kurulus>) {
        result.subscribe((res: Kurulus) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Kurulus) {
        this.eventManager.broadcast({ name: 'kurulusListModification', content: 'OK'});
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

    trackKurulusTipiById(index: number, item: KurulusTipi) {
        return item.id;
    }

    trackFaaliyetAlaniById(index: number, item: FaaliyetAlani) {
        return item.id;
    }

    trackUrunGrubuById(index: number, item: UrunGrubu) {
        return item.id;
    }

    trackKisiById(index: number, item: Kisi) {
        return item.id;
    }

    trackAdresById(index: number, item: Adres) {
        return item.id;
    }

    trackIlById(index: number, item: Il) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-kurulus-popup',
    template: ''
})
export class KurulusPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kurulusPopupService: KurulusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.kurulusPopupService
                    .open(KurulusDialogComponent as Component, params['id']);
            } else {
                this.kurulusPopupService
                    .open(KurulusDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
