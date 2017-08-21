import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TesisBilgisi } from './tesis-bilgisi.model';
import { TesisBilgisiPopupService } from './tesis-bilgisi-popup.service';
import { TesisBilgisiService } from './tesis-bilgisi.service';
import { Firma, FirmaService } from '../firma';
import { Ulke, UlkeService } from '../ulke';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-tesis-bilgisi-dialog',
    templateUrl: './tesis-bilgisi-dialog.component.html'
})
export class TesisBilgisiDialogComponent implements OnInit {

    tesisBilgisi: TesisBilgisi;
    isSaving: boolean;

    firmas: Firma[];

    bulunduguulkes: Ulke[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private tesisBilgisiService: TesisBilgisiService,
        private firmaService: FirmaService,
        private ulkeService: UlkeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.firmaService.query()
            .subscribe((res: ResponseWrapper) => { this.firmas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.ulkeService
            .query({filter: 'tesisbilgisi-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.tesisBilgisi.bulunduguUlke || !this.tesisBilgisi.bulunduguUlke.id) {
                    this.bulunduguulkes = res.json;
                } else {
                    this.ulkeService
                        .find(this.tesisBilgisi.bulunduguUlke.id)
                        .subscribe((subRes: Ulke) => {
                            this.bulunduguulkes = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tesisBilgisi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tesisBilgisiService.update(this.tesisBilgisi));
        } else {
            this.subscribeToSaveResponse(
                this.tesisBilgisiService.create(this.tesisBilgisi));
        }
    }

    private subscribeToSaveResponse(result: Observable<TesisBilgisi>) {
        result.subscribe((res: TesisBilgisi) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TesisBilgisi) {
        this.eventManager.broadcast({ name: 'tesisBilgisiListModification', content: 'OK'});
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

    trackUlkeById(index: number, item: Ulke) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tesis-bilgisi-popup',
    template: ''
})
export class TesisBilgisiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tesisBilgisiPopupService: TesisBilgisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tesisBilgisiPopupService
                    .open(TesisBilgisiDialogComponent as Component, params['id']);
            } else {
                this.tesisBilgisiPopupService
                    .open(TesisBilgisiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
