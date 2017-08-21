import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SayisalVeri } from './sayisal-veri.model';
import { SayisalVeriPopupService } from './sayisal-veri-popup.service';
import { SayisalVeriService } from './sayisal-veri.service';
import { Urun, UrunService } from '../urun';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sayisal-veri-dialog',
    templateUrl: './sayisal-veri-dialog.component.html'
})
export class SayisalVeriDialogComponent implements OnInit {

    sayisalVeri: SayisalVeri;
    isSaving: boolean;

    uruns: Urun[];
    yilDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private sayisalVeriService: SayisalVeriService,
        private urunService: UrunService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.urunService.query()
            .subscribe((res: ResponseWrapper) => { this.uruns = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sayisalVeri.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sayisalVeriService.update(this.sayisalVeri));
        } else {
            this.subscribeToSaveResponse(
                this.sayisalVeriService.create(this.sayisalVeri));
        }
    }

    private subscribeToSaveResponse(result: Observable<SayisalVeri>) {
        result.subscribe((res: SayisalVeri) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SayisalVeri) {
        this.eventManager.broadcast({ name: 'sayisalVeriListModification', content: 'OK'});
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

    trackUrunById(index: number, item: Urun) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sayisal-veri-popup',
    template: ''
})
export class SayisalVeriPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sayisalVeriPopupService: SayisalVeriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sayisalVeriPopupService
                    .open(SayisalVeriDialogComponent as Component, params['id']);
            } else {
                this.sayisalVeriPopupService
                    .open(SayisalVeriDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
