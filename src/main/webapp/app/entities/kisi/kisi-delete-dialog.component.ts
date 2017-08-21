import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Kisi } from './kisi.model';
import { KisiPopupService } from './kisi-popup.service';
import { KisiService } from './kisi.service';

@Component({
    selector: 'jhi-kisi-delete-dialog',
    templateUrl: './kisi-delete-dialog.component.html'
})
export class KisiDeleteDialogComponent {

    kisi: Kisi;

    constructor(
        private kisiService: KisiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kisiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'kisiListModification',
                content: 'Deleted an kisi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-kisi-delete-popup',
    template: ''
})
export class KisiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kisiPopupService: KisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.kisiPopupService
                .open(KisiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
