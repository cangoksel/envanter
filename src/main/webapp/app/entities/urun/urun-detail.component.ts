import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Urun } from './urun.model';
import { UrunService } from './urun.service';

@Component({
    selector: 'jhi-urun-detail',
    templateUrl: './urun-detail.component.html'
})
export class UrunDetailComponent implements OnInit, OnDestroy {

    urun: Urun;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private urunService: UrunService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUruns();
    }

    load(id) {
        this.urunService.find(id).subscribe((urun) => {
            this.urun = urun;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUruns() {
        this.eventSubscriber = this.eventManager.subscribe(
            'urunListModification',
            (response) => this.load(this.urun.id)
        );
    }
}
