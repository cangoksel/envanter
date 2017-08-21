import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FaaliyetAlani } from './faaliyet-alani.model';
import { FaaliyetAlaniPopupService } from './faaliyet-alani-popup.service';
import { FaaliyetAlaniService } from './faaliyet-alani.service';

@Component({
    selector: 'jhi-faaliyet-alani-delete-dialog',
    templateUrl: './faaliyet-alani-delete-dialog.component.html'
})
export class FaaliyetAlaniDeleteDialogComponent {

    faaliyetAlani: FaaliyetAlani;

    constructor(
        private faaliyetAlaniService: FaaliyetAlaniService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.faaliyetAlaniService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'faaliyetAlaniListModification',
                content: 'Deleted an faaliyetAlani'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-faaliyet-alani-delete-popup',
    template: ''
})
export class FaaliyetAlaniDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private faaliyetAlaniPopupService: FaaliyetAlaniPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.faaliyetAlaniPopupService
                .open(FaaliyetAlaniDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
