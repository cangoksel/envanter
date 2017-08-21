import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Kisi } from './kisi.model';
import { KisiService } from './kisi.service';

@Component({
    selector: 'jhi-kisi-detail',
    templateUrl: './kisi-detail.component.html'
})
export class KisiDetailComponent implements OnInit, OnDestroy {

    kisi: Kisi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private kisiService: KisiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKisis();
    }

    load(id) {
        this.kisiService.find(id).subscribe((kisi) => {
            this.kisi = kisi;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKisis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'kisiListModification',
            (response) => this.load(this.kisi.id)
        );
    }
}
