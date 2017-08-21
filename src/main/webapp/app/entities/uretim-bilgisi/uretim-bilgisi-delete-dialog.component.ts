import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UretimBilgisi } from './uretim-bilgisi.model';
import { UretimBilgisiPopupService } from './uretim-bilgisi-popup.service';
import { UretimBilgisiService } from './uretim-bilgisi.service';

@Component({
    selector: 'jhi-uretim-bilgisi-delete-dialog',
    templateUrl: './uretim-bilgisi-delete-dialog.component.html'
})
export class UretimBilgisiDeleteDialogComponent {

    uretimBilgisi: UretimBilgisi;

    constructor(
        private uretimBilgisiService: UretimBilgisiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.uretimBilgisiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'uretimBilgisiListModification',
                content: 'Deleted an uretimBilgisi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-uretim-bilgisi-delete-popup',
    template: ''
})
export class UretimBilgisiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private uretimBilgisiPopupService: UretimBilgisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.uretimBilgisiPopupService
                .open(UretimBilgisiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
