import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Ulke } from './ulke.model';
import { UlkeService } from './ulke.service';

@Component({
    selector: 'jhi-ulke-detail',
    templateUrl: './ulke-detail.component.html'
})
export class UlkeDetailComponent implements OnInit, OnDestroy {

    ulke: Ulke;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ulkeService: UlkeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUlkes();
    }

    load(id) {
        this.ulkeService.find(id).subscribe((ulke) => {
            this.ulke = ulke;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUlkes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ulkeListModification',
            (response) => this.load(this.ulke.id)
        );
    }
}
