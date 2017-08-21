import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Ihracat } from './ihracat.model';
import { IhracatService } from './ihracat.service';

@Component({
    selector: 'jhi-ihracat-detail',
    templateUrl: './ihracat-detail.component.html'
})
export class IhracatDetailComponent implements OnInit, OnDestroy {

    ihracat: Ihracat;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ihracatService: IhracatService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIhracats();
    }

    load(id) {
        this.ihracatService.find(id).subscribe((ihracat) => {
            this.ihracat = ihracat;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIhracats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ihracatListModification',
            (response) => this.load(this.ihracat.id)
        );
    }
}
