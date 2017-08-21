import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IsyeriBilgileri } from './isyeri-bilgileri.model';
import { IsyeriBilgileriPopupService } from './isyeri-bilgileri-popup.service';
import { IsyeriBilgileriService } from './isyeri-bilgileri.service';

@Component({
    selector: 'jhi-isyeri-bilgileri-delete-dialog',
    templateUrl: './isyeri-bilgileri-delete-dialog.component.html'
})
export class IsyeriBilgileriDeleteDialogComponent {

    isyeriBilgileri: IsyeriBilgileri;

    constructor(
        private isyeriBilgileriService: IsyeriBilgileriService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.isyeriBilgileriService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'isyeriBilgileriListModification',
                content: 'Deleted an isyeriBilgileri'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-isyeri-bilgileri-delete-popup',
    template: ''
})
export class IsyeriBilgileriDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private isyeriBilgileriPopupService: IsyeriBilgileriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.isyeriBilgileriPopupService
                .open(IsyeriBilgileriDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
