import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BelgeTipi } from './belge-tipi.model';
import { BelgeTipiPopupService } from './belge-tipi-popup.service';
import { BelgeTipiService } from './belge-tipi.service';

@Component({
    selector: 'jhi-belge-tipi-delete-dialog',
    templateUrl: './belge-tipi-delete-dialog.component.html'
})
export class BelgeTipiDeleteDialogComponent {

    belgeTipi: BelgeTipi;

    constructor(
        private belgeTipiService: BelgeTipiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.belgeTipiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'belgeTipiListModification',
                content: 'Deleted an belgeTipi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-belge-tipi-delete-popup',
    template: ''
})
export class BelgeTipiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private belgeTipiPopupService: BelgeTipiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.belgeTipiPopupService
                .open(BelgeTipiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
