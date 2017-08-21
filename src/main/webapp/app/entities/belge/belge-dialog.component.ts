import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Belge } from './belge.model';
import { BelgePopupService } from './belge-popup.service';
import { BelgeService } from './belge.service';
import { BelgeTipi, BelgeTipiService } from '../belge-tipi';
import { Urun, UrunService } from '../urun';
import { IsyeriBilgileri, IsyeriBilgileriService } from '../isyeri-bilgileri';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-belge-dialog',
    templateUrl: './belge-dialog.component.html'
})
export class BelgeDialogComponent implements OnInit {

    belge: Belge;
    isSaving: boolean;

    belgetipis: BelgeTipi[];

    uruns: Urun[];

    isyeribilgileris: IsyeriBilgileri[];
    olusturmaZamaniDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private belgeService: BelgeService,
        private belgeTipiService: BelgeTipiService,
        private urunService: UrunService,
        private isyeriBilgileriService: IsyeriBilgileriService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.belgeTipiService
            .query({filter: 'belge-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.belge.belgeTipi || !this.belge.belgeTipi.id) {
                    this.belgetipis = res.json;
                } else {
                    this.belgeTipiService
                        .find(this.belge.belgeTipi.id)
                        .subscribe((subRes: BelgeTipi) => {
                            this.belgetipis = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.urunService.query()
            .subscribe((res: ResponseWrapper) => { this.uruns = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.isyeriBilgileriService.query()
            .subscribe((res: ResponseWrapper) => { this.isyeribilgileris = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, belge, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                belge[field] = base64Data;
                belge[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.belge.id !== undefined) {
            this.subscribeToSaveResponse(
                this.belgeService.update(this.belge));
        } else {
            this.subscribeToSaveResponse(
                this.belgeService.create(this.belge));
        }
    }

    private subscribeToSaveResponse(result: Observable<Belge>) {
        result.subscribe((res: Belge) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Belge) {
        this.eventManager.broadcast({ name: 'belgeListModification', content: 'OK'});
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

    trackBelgeTipiById(index: number, item: BelgeTipi) {
        return item.id;
    }

    trackUrunById(index: number, item: Urun) {
        return item.id;
    }

    trackIsyeriBilgileriById(index: number, item: IsyeriBilgileri) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-belge-popup',
    template: ''
})
export class BelgePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private belgePopupService: BelgePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.belgePopupService
                    .open(BelgeDialogComponent as Component, params['id']);
            } else {
                this.belgePopupService
                    .open(BelgeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
