import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Kurum } from './kurum.model';
import { KurumService } from './kurum.service';

@Component({
    selector: 'jhi-kurum-detail',
    templateUrl: './kurum-detail.component.html'
})
export class KurumDetailComponent implements OnInit, OnDestroy {

    kurum: Kurum;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private kurumService: KurumService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKurums();
    }

    load(id) {
        this.kurumService.find(id).subscribe((kurum) => {
            this.kurum = kurum;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKurums() {
        this.eventSubscriber = this.eventManager.subscribe(
            'kurumListModification',
            (response) => this.load(this.kurum.id)
        );
    }
}
