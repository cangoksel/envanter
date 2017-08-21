import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { IsbirligiFirma } from './isbirligi-firma.model';
import { IsbirligiFirmaService } from './isbirligi-firma.service';

@Component({
    selector: 'jhi-isbirligi-firma-detail',
    templateUrl: './isbirligi-firma-detail.component.html'
})
export class IsbirligiFirmaDetailComponent implements OnInit, OnDestroy {

    isbirligiFirma: IsbirligiFirma;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private isbirligiFirmaService: IsbirligiFirmaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIsbirligiFirmas();
    }

    load(id) {
        this.isbirligiFirmaService.find(id).subscribe((isbirligiFirma) => {
            this.isbirligiFirma = isbirligiFirma;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIsbirligiFirmas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'isbirligiFirmaListModification',
            (response) => this.load(this.isbirligiFirma.id)
        );
    }
}
