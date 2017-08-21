import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Firma } from './firma.model';
import { FirmaPopupService } from './firma-popup.service';
import { FirmaService } from './firma.service';

@Component({
    selector: 'jhi-firma-delete-dialog',
    templateUrl: './firma-delete-dialog.component.html'
})
export class FirmaDeleteDialogComponent {

    firma: Firma;

    constructor(
        private firmaService: FirmaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.firmaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'firmaListModification',
                content: 'Deleted an firma'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-firma-delete-popup',
    template: ''
})
export class FirmaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private firmaPopupService: FirmaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.firmaPopupService
                .open(FirmaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
