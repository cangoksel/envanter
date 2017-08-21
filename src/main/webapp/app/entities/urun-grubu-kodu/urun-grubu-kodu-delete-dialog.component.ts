import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UrunGrubuKodu } from './urun-grubu-kodu.model';
import { UrunGrubuKoduPopupService } from './urun-grubu-kodu-popup.service';
import { UrunGrubuKoduService } from './urun-grubu-kodu.service';

@Component({
    selector: 'jhi-urun-grubu-kodu-delete-dialog',
    templateUrl: './urun-grubu-kodu-delete-dialog.component.html'
})
export class UrunGrubuKoduDeleteDialogComponent {

    urunGrubuKodu: UrunGrubuKodu;

    constructor(
        private urunGrubuKoduService: UrunGrubuKoduService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.urunGrubuKoduService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'urunGrubuKoduListModification',
                content: 'Deleted an urunGrubuKodu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-urun-grubu-kodu-delete-popup',
    template: ''
})
export class UrunGrubuKoduDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private urunGrubuKoduPopupService: UrunGrubuKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.urunGrubuKoduPopupService
                .open(UrunGrubuKoduDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
