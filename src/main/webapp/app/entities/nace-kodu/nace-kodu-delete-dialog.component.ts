import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NaceKodu } from './nace-kodu.model';
import { NaceKoduPopupService } from './nace-kodu-popup.service';
import { NaceKoduService } from './nace-kodu.service';

@Component({
    selector: 'jhi-nace-kodu-delete-dialog',
    templateUrl: './nace-kodu-delete-dialog.component.html'
})
export class NaceKoduDeleteDialogComponent {

    naceKodu: NaceKodu;

    constructor(
        private naceKoduService: NaceKoduService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.naceKoduService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'naceKoduListModification',
                content: 'Deleted an naceKodu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-nace-kodu-delete-popup',
    template: ''
})
export class NaceKoduDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private naceKoduPopupService: NaceKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.naceKoduPopupService
                .open(NaceKoduDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
