import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Kurulus } from './kurulus.model';
import { KurulusPopupService } from './kurulus-popup.service';
import { KurulusService } from './kurulus.service';

@Component({
    selector: 'jhi-kurulus-delete-dialog',
    templateUrl: './kurulus-delete-dialog.component.html'
})
export class KurulusDeleteDialogComponent {

    kurulus: Kurulus;

    constructor(
        private kurulusService: KurulusService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kurulusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'kurulusListModification',
                content: 'Deleted an kurulus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-kurulus-delete-popup',
    template: ''
})
export class KurulusDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kurulusPopupService: KurulusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.kurulusPopupService
                .open(KurulusDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
