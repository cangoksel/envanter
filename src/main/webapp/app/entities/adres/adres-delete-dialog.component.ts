import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Adres } from './adres.model';
import { AdresPopupService } from './adres-popup.service';
import { AdresService } from './adres.service';

@Component({
    selector: 'jhi-adres-delete-dialog',
    templateUrl: './adres-delete-dialog.component.html'
})
export class AdresDeleteDialogComponent {

    adres: Adres;

    constructor(
        private adresService: AdresService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.adresService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'adresListModification',
                content: 'Deleted an adres'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adres-delete-popup',
    template: ''
})
export class AdresDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adresPopupService: AdresPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.adresPopupService
                .open(AdresDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
