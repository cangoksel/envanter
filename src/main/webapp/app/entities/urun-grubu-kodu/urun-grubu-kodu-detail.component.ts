import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { UrunGrubuKodu } from './urun-grubu-kodu.model';
import { UrunGrubuKoduService } from './urun-grubu-kodu.service';

@Component({
    selector: 'jhi-urun-grubu-kodu-detail',
    templateUrl: './urun-grubu-kodu-detail.component.html'
})
export class UrunGrubuKoduDetailComponent implements OnInit, OnDestroy {

    urunGrubuKodu: UrunGrubuKodu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private urunGrubuKoduService: UrunGrubuKoduService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUrunGrubuKodus();
    }

    load(id) {
        this.urunGrubuKoduService.find(id).subscribe((urunGrubuKodu) => {
            this.urunGrubuKodu = urunGrubuKodu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUrunGrubuKodus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'urunGrubuKoduListModification',
            (response) => this.load(this.urunGrubuKodu.id)
        );
    }
}
