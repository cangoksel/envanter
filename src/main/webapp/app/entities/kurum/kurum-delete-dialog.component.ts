import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Kurum } from './kurum.model';
import { KurumPopupService } from './kurum-popup.service';
import { KurumService } from './kurum.service';

@Component({
    selector: 'jhi-kurum-delete-dialog',
    templateUrl: './kurum-delete-dialog.component.html'
})
export class KurumDeleteDialogComponent {

    kurum: Kurum;

    constructor(
        private kurumService: KurumService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kurumService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'kurumListModification',
                content: 'Deleted an kurum'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-kurum-delete-popup',
    template: ''
})
export class KurumDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kurumPopupService: KurumPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.kurumPopupService
                .open(KurumDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
