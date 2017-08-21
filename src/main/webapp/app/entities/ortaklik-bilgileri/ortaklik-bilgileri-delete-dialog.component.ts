import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrtaklikBilgileri } from './ortaklik-bilgileri.model';
import { OrtaklikBilgileriPopupService } from './ortaklik-bilgileri-popup.service';
import { OrtaklikBilgileriService } from './ortaklik-bilgileri.service';

@Component({
    selector: 'jhi-ortaklik-bilgileri-delete-dialog',
    templateUrl: './ortaklik-bilgileri-delete-dialog.component.html'
})
export class OrtaklikBilgileriDeleteDialogComponent {

    ortaklikBilgileri: OrtaklikBilgileri;

    constructor(
        private ortaklikBilgileriService: OrtaklikBilgileriService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ortaklikBilgileriService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ortaklikBilgileriListModification',
                content: 'Deleted an ortaklikBilgileri'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ortaklik-bilgileri-delete-popup',
    template: ''
})
export class OrtaklikBilgileriDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ortaklikBilgileriPopupService: OrtaklikBilgileriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ortaklikBilgileriPopupService
                .open(OrtaklikBilgileriDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
