import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Belge } from './belge.model';
import { BelgePopupService } from './belge-popup.service';
import { BelgeService } from './belge.service';

@Component({
    selector: 'jhi-belge-delete-dialog',
    templateUrl: './belge-delete-dialog.component.html'
})
export class BelgeDeleteDialogComponent {

    belge: Belge;

    constructor(
        private belgeService: BelgeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.belgeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'belgeListModification',
                content: 'Deleted an belge'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-belge-delete-popup',
    template: ''
})
export class BelgeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private belgePopupService: BelgePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.belgePopupService
                .open(BelgeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
