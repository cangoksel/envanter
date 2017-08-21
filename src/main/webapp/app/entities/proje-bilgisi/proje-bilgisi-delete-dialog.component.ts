import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProjeBilgisi } from './proje-bilgisi.model';
import { ProjeBilgisiPopupService } from './proje-bilgisi-popup.service';
import { ProjeBilgisiService } from './proje-bilgisi.service';

@Component({
    selector: 'jhi-proje-bilgisi-delete-dialog',
    templateUrl: './proje-bilgisi-delete-dialog.component.html'
})
export class ProjeBilgisiDeleteDialogComponent {

    projeBilgisi: ProjeBilgisi;

    constructor(
        private projeBilgisiService: ProjeBilgisiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.projeBilgisiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'projeBilgisiListModification',
                content: 'Deleted an projeBilgisi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-proje-bilgisi-delete-popup',
    template: ''
})
export class ProjeBilgisiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projeBilgisiPopupService: ProjeBilgisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.projeBilgisiPopupService
                .open(ProjeBilgisiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
