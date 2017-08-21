import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MedikalTurKodu } from './medikal-tur-kodu.model';
import { MedikalTurKoduPopupService } from './medikal-tur-kodu-popup.service';
import { MedikalTurKoduService } from './medikal-tur-kodu.service';

@Component({
    selector: 'jhi-medikal-tur-kodu-delete-dialog',
    templateUrl: './medikal-tur-kodu-delete-dialog.component.html'
})
export class MedikalTurKoduDeleteDialogComponent {

    medikalTurKodu: MedikalTurKodu;

    constructor(
        private medikalTurKoduService: MedikalTurKoduService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.medikalTurKoduService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'medikalTurKoduListModification',
                content: 'Deleted an medikalTurKodu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-medikal-tur-kodu-delete-popup',
    template: ''
})
export class MedikalTurKoduDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private medikalTurKoduPopupService: MedikalTurKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.medikalTurKoduPopupService
                .open(MedikalTurKoduDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
