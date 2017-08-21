import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { TesisBilgisi } from './tesis-bilgisi.model';
import { TesisBilgisiService } from './tesis-bilgisi.service';

@Component({
    selector: 'jhi-tesis-bilgisi-detail',
    templateUrl: './tesis-bilgisi-detail.component.html'
})
export class TesisBilgisiDetailComponent implements OnInit, OnDestroy {

    tesisBilgisi: TesisBilgisi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tesisBilgisiService: TesisBilgisiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTesisBilgisis();
    }

    load(id) {
        this.tesisBilgisiService.find(id).subscribe((tesisBilgisi) => {
            this.tesisBilgisi = tesisBilgisi;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTesisBilgisis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tesisBilgisiListModification',
            (response) => this.load(this.tesisBilgisi.id)
        );
    }
}
