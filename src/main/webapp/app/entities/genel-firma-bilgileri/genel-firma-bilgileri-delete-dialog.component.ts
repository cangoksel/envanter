import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GenelFirmaBilgileri } from './genel-firma-bilgileri.model';
import { GenelFirmaBilgileriPopupService } from './genel-firma-bilgileri-popup.service';
import { GenelFirmaBilgileriService } from './genel-firma-bilgileri.service';

@Component({
    selector: 'jhi-genel-firma-bilgileri-delete-dialog',
    templateUrl: './genel-firma-bilgileri-delete-dialog.component.html'
})
export class GenelFirmaBilgileriDeleteDialogComponent {

    genelFirmaBilgileri: GenelFirmaBilgileri;

    constructor(
        private genelFirmaBilgileriService: GenelFirmaBilgileriService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.genelFirmaBilgileriService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'genelFirmaBilgileriListModification',
                content: 'Deleted an genelFirmaBilgileri'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-genel-firma-bilgileri-delete-popup',
    template: ''
})
export class GenelFirmaBilgileriDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private genelFirmaBilgileriPopupService: GenelFirmaBilgileriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.genelFirmaBilgileriPopupService
                .open(GenelFirmaBilgileriDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
