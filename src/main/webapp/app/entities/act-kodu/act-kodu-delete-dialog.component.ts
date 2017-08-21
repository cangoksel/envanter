import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ActKodu } from './act-kodu.model';
import { ActKoduPopupService } from './act-kodu-popup.service';
import { ActKoduService } from './act-kodu.service';

@Component({
    selector: 'jhi-act-kodu-delete-dialog',
    templateUrl: './act-kodu-delete-dialog.component.html'
})
export class ActKoduDeleteDialogComponent {

    actKodu: ActKodu;

    constructor(
        private actKoduService: ActKoduService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.actKoduService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'actKoduListModification',
                content: 'Deleted an actKodu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-act-kodu-delete-popup',
    template: ''
})
export class ActKoduDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private actKoduPopupService: ActKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.actKoduPopupService
                .open(ActKoduDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
