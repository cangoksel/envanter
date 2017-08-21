import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Kurulus } from './kurulus.model';
import { KurulusService } from './kurulus.service';

@Component({
    selector: 'jhi-kurulus-detail',
    templateUrl: './kurulus-detail.component.html'
})
export class KurulusDetailComponent implements OnInit, OnDestroy {

    kurulus: Kurulus;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private kurulusService: KurulusService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKuruluses();
    }

    load(id) {
        this.kurulusService.find(id).subscribe((kurulus) => {
            this.kurulus = kurulus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKuruluses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'kurulusListModification',
            (response) => this.load(this.kurulus.id)
        );
    }
}
