import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { KurulusTipi } from './kurulus-tipi.model';
import { KurulusTipiPopupService } from './kurulus-tipi-popup.service';
import { KurulusTipiService } from './kurulus-tipi.service';

@Component({
    selector: 'jhi-kurulus-tipi-delete-dialog',
    templateUrl: './kurulus-tipi-delete-dialog.component.html'
})
export class KurulusTipiDeleteDialogComponent {

    kurulusTipi: KurulusTipi;

    constructor(
        private kurulusTipiService: KurulusTipiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kurulusTipiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'kurulusTipiListModification',
                content: 'Deleted an kurulusTipi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-kurulus-tipi-delete-popup',
    template: ''
})
export class KurulusTipiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kurulusTipiPopupService: KurulusTipiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.kurulusTipiPopupService
                .open(KurulusTipiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
