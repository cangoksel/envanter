import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SayisalVeri } from './sayisal-veri.model';
import { SayisalVeriPopupService } from './sayisal-veri-popup.service';
import { SayisalVeriService } from './sayisal-veri.service';

@Component({
    selector: 'jhi-sayisal-veri-delete-dialog',
    templateUrl: './sayisal-veri-delete-dialog.component.html'
})
export class SayisalVeriDeleteDialogComponent {

    sayisalVeri: SayisalVeri;

    constructor(
        private sayisalVeriService: SayisalVeriService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sayisalVeriService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sayisalVeriListModification',
                content: 'Deleted an sayisalVeri'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sayisal-veri-delete-popup',
    template: ''
})
export class SayisalVeriDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sayisalVeriPopupService: SayisalVeriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sayisalVeriPopupService
                .open(SayisalVeriDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
