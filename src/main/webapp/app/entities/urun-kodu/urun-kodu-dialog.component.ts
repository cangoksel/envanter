import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UrunKodu } from './urun-kodu.model';
import { UrunKoduPopupService } from './urun-kodu-popup.service';
import { UrunKoduService } from './urun-kodu.service';
import { UrunGrubuKodu, UrunGrubuKoduService } from '../urun-grubu-kodu';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-urun-kodu-dialog',
    templateUrl: './urun-kodu-dialog.component.html'
})
export class UrunKoduDialogComponent implements OnInit {

    urunKodu: UrunKodu;
    isSaving: boolean;

    urungrubukodus: UrunGrubuKodu[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private urunKoduService: UrunKoduService,
        private urunGrubuKoduService: UrunGrubuKoduService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.urunGrubuKoduService
            .query({filter: 'urunkodu-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.urunKodu.urunGrubuKodu || !this.urunKodu.urunGrubuKodu.id) {
                    this.urungrubukodus = res.json;
                } else {
                    this.urunGrubuKoduService
                        .find(this.urunKodu.urunGrubuKodu.id)
                        .subscribe((subRes: UrunGrubuKodu) => {
                            this.urungrubukodus = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.urunKodu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.urunKoduService.update(this.urunKodu));
        } else {
            this.subscribeToSaveResponse(
                this.urunKoduService.create(this.urunKodu));
        }
    }

    private subscribeToSaveResponse(result: Observable<UrunKodu>) {
        result.subscribe((res: UrunKodu) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: UrunKodu) {
        this.eventManager.broadcast({ name: 'urunKoduListModification', content: 'OK'});
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

    trackUrunGrubuKoduById(index: number, item: UrunGrubuKodu) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-urun-kodu-popup',
    template: ''
})
export class UrunKoduPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private urunKoduPopupService: UrunKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.urunKoduPopupService
                    .open(UrunKoduDialogComponent as Component, params['id']);
            } else {
                this.urunKoduPopupService
                    .open(UrunKoduDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
