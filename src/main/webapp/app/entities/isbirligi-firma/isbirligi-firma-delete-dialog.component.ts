import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IsbirligiFirma } from './isbirligi-firma.model';
import { IsbirligiFirmaPopupService } from './isbirligi-firma-popup.service';
import { IsbirligiFirmaService } from './isbirligi-firma.service';

@Component({
    selector: 'jhi-isbirligi-firma-delete-dialog',
    templateUrl: './isbirligi-firma-delete-dialog.component.html'
})
export class IsbirligiFirmaDeleteDialogComponent {

    isbirligiFirma: IsbirligiFirma;

    constructor(
        private isbirligiFirmaService: IsbirligiFirmaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.isbirligiFirmaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'isbirligiFirmaListModification',
                content: 'Deleted an isbirligiFirma'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-isbirligi-firma-delete-popup',
    template: ''
})
export class IsbirligiFirmaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private isbirligiFirmaPopupService: IsbirligiFirmaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.isbirligiFirmaPopupService
                .open(IsbirligiFirmaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
