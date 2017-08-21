import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CalisanTipi } from './calisan-tipi.model';
import { CalisanTipiPopupService } from './calisan-tipi-popup.service';
import { CalisanTipiService } from './calisan-tipi.service';

@Component({
    selector: 'jhi-calisan-tipi-delete-dialog',
    templateUrl: './calisan-tipi-delete-dialog.component.html'
})
export class CalisanTipiDeleteDialogComponent {

    calisanTipi: CalisanTipi;

    constructor(
        private calisanTipiService: CalisanTipiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.calisanTipiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'calisanTipiListModification',
                content: 'Deleted an calisanTipi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-calisan-tipi-delete-popup',
    template: ''
})
export class CalisanTipiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private calisanTipiPopupService: CalisanTipiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.calisanTipiPopupService
                .open(CalisanTipiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
