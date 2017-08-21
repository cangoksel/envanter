import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Urun } from './urun.model';
import { UrunPopupService } from './urun-popup.service';
import { UrunService } from './urun.service';

@Component({
    selector: 'jhi-urun-delete-dialog',
    templateUrl: './urun-delete-dialog.component.html'
})
export class UrunDeleteDialogComponent {

    urun: Urun;

    constructor(
        private urunService: UrunService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.urunService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'urunListModification',
                content: 'Deleted an urun'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-urun-delete-popup',
    template: ''
})
export class UrunDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private urunPopupService: UrunPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.urunPopupService
                .open(UrunDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
