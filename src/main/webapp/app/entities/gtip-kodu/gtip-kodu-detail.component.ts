import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { GtipKodu } from './gtip-kodu.model';
import { GtipKoduService } from './gtip-kodu.service';

@Component({
    selector: 'jhi-gtip-kodu-detail',
    templateUrl: './gtip-kodu-detail.component.html'
})
export class GtipKoduDetailComponent implements OnInit, OnDestroy {

    gtipKodu: GtipKodu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private gtipKoduService: GtipKoduService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGtipKodus();
    }

    load(id) {
        this.gtipKoduService.find(id).subscribe((gtipKodu) => {
            this.gtipKodu = gtipKodu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGtipKodus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'gtipKoduListModification',
            (response) => this.load(this.gtipKodu.id)
        );
    }
}
