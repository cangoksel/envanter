import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { UrunKodu } from './urun-kodu.model';
import { UrunKoduService } from './urun-kodu.service';

@Component({
    selector: 'jhi-urun-kodu-detail',
    templateUrl: './urun-kodu-detail.component.html'
})
export class UrunKoduDetailComponent implements OnInit, OnDestroy {

    urunKodu: UrunKodu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private urunKoduService: UrunKoduService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUrunKodus();
    }

    load(id) {
        this.urunKoduService.find(id).subscribe((urunKodu) => {
            this.urunKodu = urunKodu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUrunKodus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'urunKoduListModification',
            (response) => this.load(this.urunKodu.id)
        );
    }
}
