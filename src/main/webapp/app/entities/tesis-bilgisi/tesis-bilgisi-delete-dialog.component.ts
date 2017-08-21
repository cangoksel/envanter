import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TesisBilgisi } from './tesis-bilgisi.model';
import { TesisBilgisiPopupService } from './tesis-bilgisi-popup.service';
import { TesisBilgisiService } from './tesis-bilgisi.service';

@Component({
    selector: 'jhi-tesis-bilgisi-delete-dialog',
    templateUrl: './tesis-bilgisi-delete-dialog.component.html'
})
export class TesisBilgisiDeleteDialogComponent {

    tesisBilgisi: TesisBilgisi;

    constructor(
        private tesisBilgisiService: TesisBilgisiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tesisBilgisiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tesisBilgisiListModification',
                content: 'Deleted an tesisBilgisi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tesis-bilgisi-delete-popup',
    template: ''
})
export class TesisBilgisiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tesisBilgisiPopupService: TesisBilgisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tesisBilgisiPopupService
                .open(TesisBilgisiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
