import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ulke } from './ulke.model';
import { UlkePopupService } from './ulke-popup.service';
import { UlkeService } from './ulke.service';

@Component({
    selector: 'jhi-ulke-delete-dialog',
    templateUrl: './ulke-delete-dialog.component.html'
})
export class UlkeDeleteDialogComponent {

    ulke: Ulke;

    constructor(
        private ulkeService: UlkeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ulkeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ulkeListModification',
                content: 'Deleted an ulke'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ulke-delete-popup',
    template: ''
})
export class UlkeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ulkePopupService: UlkePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ulkePopupService
                .open(UlkeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
