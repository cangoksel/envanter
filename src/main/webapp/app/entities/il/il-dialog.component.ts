import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Il } from './il.model';
import { IlPopupService } from './il-popup.service';
import { IlService } from './il.service';
import { Ulke, UlkeService } from '../ulke';
import { Bolge, BolgeService } from '../bolge';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-il-dialog',
    templateUrl: './il-dialog.component.html'
})
export class IlDialogComponent implements OnInit {

    il: Il;
    isSaving: boolean;

    ulkes: Ulke[];

    bolges: Bolge[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private ilService: IlService,
        private ulkeService: UlkeService,
        private bolgeService: BolgeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.ulkeService
            .query({filter: 'il-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.il.ulke || !this.il.ulke.id) {
                    this.ulkes = res.json;
                } else {
                    this.ulkeService
                        .find(this.il.ulke.id)
                        .subscribe((subRes: Ulke) => {
                            this.ulkes = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.bolgeService
            .query({filter: 'il-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.il.bolge || !this.il.bolge.id) {
                    this.bolges = res.json;
                } else {
                    this.bolgeService
                        .find(this.il.bolge.id)
                        .subscribe((subRes: Bolge) => {
                            this.bolges = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.il.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ilService.update(this.il));
        } else {
            this.subscribeToSaveResponse(
                this.ilService.create(this.il));
        }
    }

    private subscribeToSaveResponse(result: Observable<Il>) {
        result.subscribe((res: Il) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Il) {
        this.eventManager.broadcast({ name: 'ilListModification', content: 'OK'});
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

    trackUlkeById(index: number, item: Ulke) {
        return item.id;
    }

    trackBolgeById(index: number, item: Bolge) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-il-popup',
    template: ''
})
export class IlPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ilPopupService: IlPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ilPopupService
                    .open(IlDialogComponent as Component, params['id']);
            } else {
                this.ilPopupService
                    .open(IlDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
