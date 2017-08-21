import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Il } from './il.model';
import { IlService } from './il.service';

@Component({
    selector: 'jhi-il-detail',
    templateUrl: './il-detail.component.html'
})
export class IlDetailComponent implements OnInit, OnDestroy {

    il: Il;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ilService: IlService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIls();
    }

    load(id) {
        this.ilService.find(id).subscribe((il) => {
            this.il = il;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIls() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ilListModification',
            (response) => this.load(this.il.id)
        );
    }
}
