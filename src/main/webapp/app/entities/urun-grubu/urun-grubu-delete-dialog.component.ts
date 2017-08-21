import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UrunGrubu } from './urun-grubu.model';
import { UrunGrubuPopupService } from './urun-grubu-popup.service';
import { UrunGrubuService } from './urun-grubu.service';

@Component({
    selector: 'jhi-urun-grubu-delete-dialog',
    templateUrl: './urun-grubu-delete-dialog.component.html'
})
export class UrunGrubuDeleteDialogComponent {

    urunGrubu: UrunGrubu;

    constructor(
        private urunGrubuService: UrunGrubuService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.urunGrubuService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'urunGrubuListModification',
                content: 'Deleted an urunGrubu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-urun-grubu-delete-popup',
    template: ''
})
export class UrunGrubuDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private urunGrubuPopupService: UrunGrubuPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.urunGrubuPopupService
                .open(UrunGrubuDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
