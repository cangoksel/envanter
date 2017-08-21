import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ihracat } from './ihracat.model';
import { IhracatPopupService } from './ihracat-popup.service';
import { IhracatService } from './ihracat.service';

@Component({
    selector: 'jhi-ihracat-delete-dialog',
    templateUrl: './ihracat-delete-dialog.component.html'
})
export class IhracatDeleteDialogComponent {

    ihracat: Ihracat;

    constructor(
        private ihracatService: IhracatService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ihracatService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ihracatListModification',
                content: 'Deleted an ihracat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ihracat-delete-popup',
    template: ''
})
export class IhracatDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ihracatPopupService: IhracatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ihracatPopupService
                .open(IhracatDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
