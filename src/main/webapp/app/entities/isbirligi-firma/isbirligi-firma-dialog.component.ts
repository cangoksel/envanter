import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IsbirligiFirma } from './isbirligi-firma.model';
import { IsbirligiFirmaPopupService } from './isbirligi-firma-popup.service';
import { IsbirligiFirmaService } from './isbirligi-firma.service';
import { OrtaklikBilgileri, OrtaklikBilgileriService } from '../ortaklik-bilgileri';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-isbirligi-firma-dialog',
    templateUrl: './isbirligi-firma-dialog.component.html'
})
export class IsbirligiFirmaDialogComponent implements OnInit {

    isbirligiFirma: IsbirligiFirma;
    isSaving: boolean;

    ortaklikbilgileris: OrtaklikBilgileri[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private isbirligiFirmaService: IsbirligiFirmaService,
        private ortaklikBilgileriService: OrtaklikBilgileriService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.ortaklikBilgileriService.query()
            .subscribe((res: ResponseWrapper) => { this.ortaklikbilgileris = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.isbirligiFirma.id !== undefined) {
            this.subscribeToSaveResponse(
                this.isbirligiFirmaService.update(this.isbirligiFirma));
        } else {
            this.subscribeToSaveResponse(
                this.isbirligiFirmaService.create(this.isbirligiFirma));
        }
    }

    private subscribeToSaveResponse(result: Observable<IsbirligiFirma>) {
        result.subscribe((res: IsbirligiFirma) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: IsbirligiFirma) {
        this.eventManager.broadcast({ name: 'isbirligiFirmaListModification', content: 'OK'});
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

    trackOrtaklikBilgileriById(index: number, item: OrtaklikBilgileri) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-isbirligi-firma-popup',
    template: ''
})
export class IsbirligiFirmaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private isbirligiFirmaPopupService: IsbirligiFirmaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.isbirligiFirmaPopupService
                    .open(IsbirligiFirmaDialogComponent as Component, params['id']);
            } else {
                this.isbirligiFirmaPopupService
                    .open(IsbirligiFirmaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
