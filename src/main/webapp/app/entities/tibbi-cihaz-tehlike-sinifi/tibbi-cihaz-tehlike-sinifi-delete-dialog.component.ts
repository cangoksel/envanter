import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TibbiCihazTehlikeSinifi } from './tibbi-cihaz-tehlike-sinifi.model';
import { TibbiCihazTehlikeSinifiPopupService } from './tibbi-cihaz-tehlike-sinifi-popup.service';
import { TibbiCihazTehlikeSinifiService } from './tibbi-cihaz-tehlike-sinifi.service';

@Component({
    selector: 'jhi-tibbi-cihaz-tehlike-sinifi-delete-dialog',
    templateUrl: './tibbi-cihaz-tehlike-sinifi-delete-dialog.component.html'
})
export class TibbiCihazTehlikeSinifiDeleteDialogComponent {

    tibbiCihazTehlikeSinifi: TibbiCihazTehlikeSinifi;

    constructor(
        private tibbiCihazTehlikeSinifiService: TibbiCihazTehlikeSinifiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tibbiCihazTehlikeSinifiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tibbiCihazTehlikeSinifiListModification',
                content: 'Deleted an tibbiCihazTehlikeSinifi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tibbi-cihaz-tehlike-sinifi-delete-popup',
    template: ''
})
export class TibbiCihazTehlikeSinifiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tibbiCihazTehlikeSinifiPopupService: TibbiCihazTehlikeSinifiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tibbiCihazTehlikeSinifiPopupService
                .open(TibbiCihazTehlikeSinifiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
