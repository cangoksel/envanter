import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { TibbiCihazTehlikeSinifi } from './tibbi-cihaz-tehlike-sinifi.model';
import { TibbiCihazTehlikeSinifiService } from './tibbi-cihaz-tehlike-sinifi.service';

@Component({
    selector: 'jhi-tibbi-cihaz-tehlike-sinifi-detail',
    templateUrl: './tibbi-cihaz-tehlike-sinifi-detail.component.html'
})
export class TibbiCihazTehlikeSinifiDetailComponent implements OnInit, OnDestroy {

    tibbiCihazTehlikeSinifi: TibbiCihazTehlikeSinifi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tibbiCihazTehlikeSinifiService: TibbiCihazTehlikeSinifiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTibbiCihazTehlikeSinifis();
    }

    load(id) {
        this.tibbiCihazTehlikeSinifiService.find(id).subscribe((tibbiCihazTehlikeSinifi) => {
            this.tibbiCihazTehlikeSinifi = tibbiCihazTehlikeSinifi;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTibbiCihazTehlikeSinifis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tibbiCihazTehlikeSinifiListModification',
            (response) => this.load(this.tibbiCihazTehlikeSinifi.id)
        );
    }
}
