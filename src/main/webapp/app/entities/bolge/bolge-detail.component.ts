import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Bolge } from './bolge.model';
import { BolgeService } from './bolge.service';

@Component({
    selector: 'jhi-bolge-detail',
    templateUrl: './bolge-detail.component.html'
})
export class BolgeDetailComponent implements OnInit, OnDestroy {

    bolge: Bolge;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bolgeService: BolgeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBolges();
    }

    load(id) {
        this.bolgeService.find(id).subscribe((bolge) => {
            this.bolge = bolge;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBolges() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bolgeListModification',
            (response) => this.load(this.bolge.id)
        );
    }
}
