import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UrunAltKodu } from './urun-alt-kodu.model';
import { UrunAltKoduPopupService } from './urun-alt-kodu-popup.service';
import { UrunAltKoduService } from './urun-alt-kodu.service';

@Component({
    selector: 'jhi-urun-alt-kodu-delete-dialog',
    templateUrl: './urun-alt-kodu-delete-dialog.component.html'
})
export class UrunAltKoduDeleteDialogComponent {

    urunAltKodu: UrunAltKodu;

    constructor(
        private urunAltKoduService: UrunAltKoduService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.urunAltKoduService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'urunAltKoduListModification',
                content: 'Deleted an urunAltKodu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-urun-alt-kodu-delete-popup',
    template: ''
})
export class UrunAltKoduDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private urunAltKoduPopupService: UrunAltKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.urunAltKoduPopupService
                .open(UrunAltKoduDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
