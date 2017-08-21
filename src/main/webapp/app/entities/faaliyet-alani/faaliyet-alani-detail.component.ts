import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { FaaliyetAlani } from './faaliyet-alani.model';
import { FaaliyetAlaniService } from './faaliyet-alani.service';

@Component({
    selector: 'jhi-faaliyet-alani-detail',
    templateUrl: './faaliyet-alani-detail.component.html'
})
export class FaaliyetAlaniDetailComponent implements OnInit, OnDestroy {

    faaliyetAlani: FaaliyetAlani;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private faaliyetAlaniService: FaaliyetAlaniService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFaaliyetAlanis();
    }

    load(id) {
        this.faaliyetAlaniService.find(id).subscribe((faaliyetAlani) => {
            this.faaliyetAlani = faaliyetAlani;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFaaliyetAlanis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'faaliyetAlaniListModification',
            (response) => this.load(this.faaliyetAlani.id)
        );
    }
}
