import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ProdkomKodu } from './prodkom-kodu.model';
import { ProdkomKoduService } from './prodkom-kodu.service';

@Component({
    selector: 'jhi-prodkom-kodu-detail',
    templateUrl: './prodkom-kodu-detail.component.html'
})
export class ProdkomKoduDetailComponent implements OnInit, OnDestroy {

    prodkomKodu: ProdkomKodu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private prodkomKoduService: ProdkomKoduService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProdkomKodus();
    }

    load(id) {
        this.prodkomKoduService.find(id).subscribe((prodkomKodu) => {
            this.prodkomKodu = prodkomKodu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProdkomKodus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'prodkomKoduListModification',
            (response) => this.load(this.prodkomKodu.id)
        );
    }
}
