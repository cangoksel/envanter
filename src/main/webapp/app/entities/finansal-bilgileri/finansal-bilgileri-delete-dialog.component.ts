import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FinansalBilgileri } from './finansal-bilgileri.model';
import { FinansalBilgileriPopupService } from './finansal-bilgileri-popup.service';
import { FinansalBilgileriService } from './finansal-bilgileri.service';

@Component({
    selector: 'jhi-finansal-bilgileri-delete-dialog',
    templateUrl: './finansal-bilgileri-delete-dialog.component.html'
})
export class FinansalBilgileriDeleteDialogComponent {

    finansalBilgileri: FinansalBilgileri;

    constructor(
        private finansalBilgileriService: FinansalBilgileriService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.finansalBilgileriService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'finansalBilgileriListModification',
                content: 'Deleted an finansalBilgileri'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-finansal-bilgileri-delete-popup',
    template: ''
})
export class FinansalBilgileriDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private finansalBilgileriPopupService: FinansalBilgileriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.finansalBilgileriPopupService
                .open(FinansalBilgileriDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
