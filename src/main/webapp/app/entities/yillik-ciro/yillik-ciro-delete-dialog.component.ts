import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { YillikCiro } from './yillik-ciro.model';
import { YillikCiroPopupService } from './yillik-ciro-popup.service';
import { YillikCiroService } from './yillik-ciro.service';

@Component({
    selector: 'jhi-yillik-ciro-delete-dialog',
    templateUrl: './yillik-ciro-delete-dialog.component.html'
})
export class YillikCiroDeleteDialogComponent {

    yillikCiro: YillikCiro;

    constructor(
        private yillikCiroService: YillikCiroService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.yillikCiroService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'yillikCiroListModification',
                content: 'Deleted an yillikCiro'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-yillik-ciro-delete-popup',
    template: ''
})
export class YillikCiroDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private yillikCiroPopupService: YillikCiroPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.yillikCiroPopupService
                .open(YillikCiroDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
