import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GtipKodu } from './gtip-kodu.model';
import { GtipKoduPopupService } from './gtip-kodu-popup.service';
import { GtipKoduService } from './gtip-kodu.service';

@Component({
    selector: 'jhi-gtip-kodu-delete-dialog',
    templateUrl: './gtip-kodu-delete-dialog.component.html'
})
export class GtipKoduDeleteDialogComponent {

    gtipKodu: GtipKodu;

    constructor(
        private gtipKoduService: GtipKoduService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gtipKoduService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'gtipKoduListModification',
                content: 'Deleted an gtipKodu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-gtip-kodu-delete-popup',
    template: ''
})
export class GtipKoduDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gtipKoduPopupService: GtipKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.gtipKoduPopupService
                .open(GtipKoduDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
