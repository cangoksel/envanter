import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Adres } from './adres.model';
import { AdresService } from './adres.service';

@Component({
    selector: 'jhi-adres-detail',
    templateUrl: './adres-detail.component.html'
})
export class AdresDetailComponent implements OnInit, OnDestroy {

    adres: Adres;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private adresService: AdresService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAdres();
    }

    load(id) {
        this.adresService.find(id).subscribe((adres) => {
            this.adres = adres;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAdres() {
        this.eventSubscriber = this.eventManager.subscribe(
            'adresListModification',
            (response) => this.load(this.adres.id)
        );
    }
}
