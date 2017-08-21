import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { BelgeTipi } from './belge-tipi.model';
import { BelgeTipiService } from './belge-tipi.service';

@Component({
    selector: 'jhi-belge-tipi-detail',
    templateUrl: './belge-tipi-detail.component.html'
})
export class BelgeTipiDetailComponent implements OnInit, OnDestroy {

    belgeTipi: BelgeTipi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private belgeTipiService: BelgeTipiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBelgeTipis();
    }

    load(id) {
        this.belgeTipiService.find(id).subscribe((belgeTipi) => {
            this.belgeTipi = belgeTipi;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBelgeTipis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'belgeTipiListModification',
            (response) => this.load(this.belgeTipi.id)
        );
    }
}
