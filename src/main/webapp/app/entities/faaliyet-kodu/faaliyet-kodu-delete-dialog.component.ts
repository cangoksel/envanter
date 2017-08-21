import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FaaliyetKodu } from './faaliyet-kodu.model';
import { FaaliyetKoduPopupService } from './faaliyet-kodu-popup.service';
import { FaaliyetKoduService } from './faaliyet-kodu.service';

@Component({
    selector: 'jhi-faaliyet-kodu-delete-dialog',
    templateUrl: './faaliyet-kodu-delete-dialog.component.html'
})
export class FaaliyetKoduDeleteDialogComponent {

    faaliyetKodu: FaaliyetKodu;

    constructor(
        private faaliyetKoduService: FaaliyetKoduService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.faaliyetKoduService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'faaliyetKoduListModification',
                content: 'Deleted an faaliyetKodu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-faaliyet-kodu-delete-popup',
    template: ''
})
export class FaaliyetKoduDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private faaliyetKoduPopupService: FaaliyetKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.faaliyetKoduPopupService
                .open(FaaliyetKoduDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
