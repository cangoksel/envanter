import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bolge } from './bolge.model';
import { BolgePopupService } from './bolge-popup.service';
import { BolgeService } from './bolge.service';

@Component({
    selector: 'jhi-bolge-delete-dialog',
    templateUrl: './bolge-delete-dialog.component.html'
})
export class BolgeDeleteDialogComponent {

    bolge: Bolge;

    constructor(
        private bolgeService: BolgeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bolgeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bolgeListModification',
                content: 'Deleted an bolge'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bolge-delete-popup',
    template: ''
})
export class BolgeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bolgePopupService: BolgePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bolgePopupService
                .open(BolgeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
