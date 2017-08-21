import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProdkomKodu } from './prodkom-kodu.model';
import { ProdkomKoduPopupService } from './prodkom-kodu-popup.service';
import { ProdkomKoduService } from './prodkom-kodu.service';

@Component({
    selector: 'jhi-prodkom-kodu-delete-dialog',
    templateUrl: './prodkom-kodu-delete-dialog.component.html'
})
export class ProdkomKoduDeleteDialogComponent {

    prodkomKodu: ProdkomKodu;

    constructor(
        private prodkomKoduService: ProdkomKoduService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.prodkomKoduService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'prodkomKoduListModification',
                content: 'Deleted an prodkomKodu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prodkom-kodu-delete-popup',
    template: ''
})
export class ProdkomKoduDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prodkomKoduPopupService: ProdkomKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.prodkomKoduPopupService
                .open(ProdkomKoduDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
