import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MkysKodu } from './mkys-kodu.model';
import { MkysKoduPopupService } from './mkys-kodu-popup.service';
import { MkysKoduService } from './mkys-kodu.service';

@Component({
    selector: 'jhi-mkys-kodu-delete-dialog',
    templateUrl: './mkys-kodu-delete-dialog.component.html'
})
export class MkysKoduDeleteDialogComponent {

    mkysKodu: MkysKodu;

    constructor(
        private mkysKoduService: MkysKoduService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mkysKoduService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mkysKoduListModification',
                content: 'Deleted an mkysKodu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mkys-kodu-delete-popup',
    template: ''
})
export class MkysKoduDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mkysKoduPopupService: MkysKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mkysKoduPopupService
                .open(MkysKoduDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
