import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Adres } from './adres.model';
import { AdresPopupService } from './adres-popup.service';
import { AdresService } from './adres.service';
import { Il, IlService } from '../il';
import { Ulke, UlkeService } from '../ulke';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-adres-dialog',
    templateUrl: './adres-dialog.component.html'
})
export class AdresDialogComponent implements OnInit {

    adres: Adres;
    isSaving: boolean;

    ils: Il[];

    ulkes: Ulke[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private adresService: AdresService,
        private ilService: IlService,
        private ulkeService: UlkeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.ilService
            .query({filter: 'adres-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.adres.il || !this.adres.il.id) {
                    this.ils = res.json;
                } else {
                    this.ilService
                        .find(this.adres.il.id)
                        .subscribe((subRes: Il) => {
                            this.ils = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.ulkeService
            .query({filter: 'adres-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.adres.ulke || !this.adres.ulke.id) {
                    this.ulkes = res.json;
                } else {
                    this.ulkeService
                        .find(this.adres.ulke.id)
                        .subscribe((subRes: Ulke) => {
                            this.ulkes = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.adres.id !== undefined) {
            this.subscribeToSaveResponse(
                this.adresService.update(this.adres));
        } else {
            this.subscribeToSaveResponse(
                this.adresService.create(this.adres));
        }
    }

    private subscribeToSaveResponse(result: Observable<Adres>) {
        result.subscribe((res: Adres) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Adres) {
        this.eventManager.broadcast({ name: 'adresListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-adres-popup',
    template: ''
})
export class AdresPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adresPopupService: AdresPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.adresPopupService
                    .open(AdresDialogComponent as Component, params['id']);
            } else {
                this.adresPopupService
                    .open(AdresDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
