import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { YillikCiro } from './yillik-ciro.model';
import { YillikCiroPopupService } from './yillik-ciro-popup.service';
import { YillikCiroService } from './yillik-ciro.service';
import { FinansalBilgileri, FinansalBilgileriService } from '../finansal-bilgileri';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-yillik-ciro-dialog',
    templateUrl: './yillik-ciro-dialog.component.html'
})
export class YillikCiroDialogComponent implements OnInit {

    yillikCiro: YillikCiro;
    isSaving: boolean;

    finansalbilgileris: FinansalBilgileri[];
    yilDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private yillikCiroService: YillikCiroService,
        private finansalBilgileriService: FinansalBilgileriService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.finansalBilgileriService.query()
            .subscribe((res: ResponseWrapper) => { this.finansalbilgileris = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.yillikCiro.id !== undefined) {
            this.subscribeToSaveResponse(
                this.yillikCiroService.update(this.yillikCiro));
        } else {
            this.subscribeToSaveResponse(
                this.yillikCiroService.create(this.yillikCiro));
        }
    }

    private subscribeToSaveResponse(result: Observable<YillikCiro>) {
        result.subscribe((res: YillikCiro) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: YillikCiro) {
        this.eventManager.broadcast({ name: 'yillikCiroListModification', content: 'OK'});
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

    trackFinansalBilgileriById(index: number, item: FinansalBilgileri) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-yillik-ciro-popup',
    template: ''
})
export class YillikCiroPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private yillikCiroPopupService: YillikCiroPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.yillikCiroPopupService
                    .open(YillikCiroDialogComponent as Component, params['id']);
            } else {
                this.yillikCiroPopupService
                    .open(YillikCiroDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
