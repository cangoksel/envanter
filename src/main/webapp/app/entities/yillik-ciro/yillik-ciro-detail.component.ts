import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { YillikCiro } from './yillik-ciro.model';
import { YillikCiroService } from './yillik-ciro.service';

@Component({
    selector: 'jhi-yillik-ciro-detail',
    templateUrl: './yillik-ciro-detail.component.html'
})
export class YillikCiroDetailComponent implements OnInit, OnDestroy {

    yillikCiro: YillikCiro;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private yillikCiroService: YillikCiroService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInYillikCiros();
    }

    load(id) {
        this.yillikCiroService.find(id).subscribe((yillikCiro) => {
            this.yillikCiro = yillikCiro;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInYillikCiros() {
        this.eventSubscriber = this.eventManager.subscribe(
            'yillikCiroListModification',
            (response) => this.load(this.yillikCiro.id)
        );
    }
}
