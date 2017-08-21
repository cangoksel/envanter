import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { UrunAltKodu } from './urun-alt-kodu.model';
import { UrunAltKoduService } from './urun-alt-kodu.service';

@Component({
    selector: 'jhi-urun-alt-kodu-detail',
    templateUrl: './urun-alt-kodu-detail.component.html'
})
export class UrunAltKoduDetailComponent implements OnInit, OnDestroy {

    urunAltKodu: UrunAltKodu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private urunAltKoduService: UrunAltKoduService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUrunAltKodus();
    }

    load(id) {
        this.urunAltKoduService.find(id).subscribe((urunAltKodu) => {
            this.urunAltKodu = urunAltKodu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUrunAltKodus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'urunAltKoduListModification',
            (response) => this.load(this.urunAltKodu.id)
        );
    }
}
