import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UrunKodu } from './urun-kodu.model';
import { UrunKoduPopupService } from './urun-kodu-popup.service';
import { UrunKoduService } from './urun-kodu.service';

@Component({
    selector: 'jhi-urun-kodu-delete-dialog',
    templateUrl: './urun-kodu-delete-dialog.component.html'
})
export class UrunKoduDeleteDialogComponent {

    urunKodu: UrunKodu;

    constructor(
        private urunKoduService: UrunKoduService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.urunKoduService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'urunKoduListModification',
                content: 'Deleted an urunKodu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-urun-kodu-delete-popup',
    template: ''
})
export class UrunKoduDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private urunKoduPopupService: UrunKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.urunKoduPopupService
                .open(UrunKoduDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
