import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Il } from './il.model';
import { IlPopupService } from './il-popup.service';
import { IlService } from './il.service';

@Component({
    selector: 'jhi-il-delete-dialog',
    templateUrl: './il-delete-dialog.component.html'
})
export class IlDeleteDialogComponent {

    il: Il;

    constructor(
        private ilService: IlService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ilService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ilListModification',
                content: 'Deleted an il'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-il-delete-popup',
    template: ''
})
export class IlDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ilPopupService: IlPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ilPopupService
                .open(IlDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
