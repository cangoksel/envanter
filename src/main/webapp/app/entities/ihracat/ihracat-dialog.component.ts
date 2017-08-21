import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ihracat } from './ihracat.model';
import { IhracatPopupService } from './ihracat-popup.service';
import { IhracatService } from './ihracat.service';
import { FinansalBilgileri, FinansalBilgileriService } from '../finansal-bilgileri';
import { Ulke, UlkeService } from '../ulke';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ihracat-dialog',
    templateUrl: './ihracat-dialog.component.html'
})
export class IhracatDialogComponent implements OnInit {

    ihracat: Ihracat;
    isSaving: boolean;

    finansalbilgileris: FinansalBilgileri[];

    yapilanulkes: Ulke[];
    yilDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private ihracatService: IhracatService,
        private finansalBilgileriService: FinansalBilgileriService,
        private ulkeService: UlkeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.finansalBilgileriService.query()
            .subscribe((res: ResponseWrapper) => { this.finansalbilgileris = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.ulkeService
            .query({filter: 'ihracat-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.ihracat.yapilanUlke || !this.ihracat.yapilanUlke.id) {
                    this.yapilanulkes = res.json;
                } else {
                    this.ulkeService
                        .find(this.ihracat.yapilanUlke.id)
                        .subscribe((subRes: Ulke) => {
                            this.yapilanulkes = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ihracat.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ihracatService.update(this.ihracat));
        } else {
            this.subscribeToSaveResponse(
                this.ihracatService.create(this.ihracat));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ihracat>) {
        result.subscribe((res: Ihracat) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Ihracat) {
        this.eventManager.broadcast({ name: 'ihracatListModification', content: 'OK'});
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

    trackUlkeById(index: number, item: Ulke) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ihracat-popup',
    template: ''
})
export class IhracatPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ihracatPopupService: IhracatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ihracatPopupService
                    .open(IhracatDialogComponent as Component, params['id']);
            } else {
                this.ihracatPopupService
                    .open(IhracatDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
