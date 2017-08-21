import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Firma } from './firma.model';
import { FirmaService } from './firma.service';

@Component({
    selector: 'jhi-firma-detail',
    templateUrl: './firma-detail.component.html'
})
export class FirmaDetailComponent implements OnInit, OnDestroy {

    firma: Firma;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private firmaService: FirmaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFirmas();
    }

    load(id) {
        this.firmaService.find(id).subscribe((firma) => {
            this.firma = firma;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFirmas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'firmaListModification',
            (response) => this.load(this.firma.id)
        );
    }
}
