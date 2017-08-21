import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CalisanSayiBilgisi } from './calisan-sayi-bilgisi.model';
import { CalisanSayiBilgisiPopupService } from './calisan-sayi-bilgisi-popup.service';
import { CalisanSayiBilgisiService } from './calisan-sayi-bilgisi.service';

@Component({
    selector: 'jhi-calisan-sayi-bilgisi-delete-dialog',
    templateUrl: './calisan-sayi-bilgisi-delete-dialog.component.html'
})
export class CalisanSayiBilgisiDeleteDialogComponent {

    calisanSayiBilgisi: CalisanSayiBilgisi;

    constructor(
        private calisanSayiBilgisiService: CalisanSayiBilgisiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.calisanSayiBilgisiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'calisanSayiBilgisiListModification',
                content: 'Deleted an calisanSayiBilgisi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-calisan-sayi-bilgisi-delete-popup',
    template: ''
})
export class CalisanSayiBilgisiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private calisanSayiBilgisiPopupService: CalisanSayiBilgisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.calisanSayiBilgisiPopupService
                .open(CalisanSayiBilgisiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
