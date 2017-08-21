import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Belge } from './belge.model';
import { BelgeService } from './belge.service';

@Component({
    selector: 'jhi-belge-detail',
    templateUrl: './belge-detail.component.html'
})
export class BelgeDetailComponent implements OnInit, OnDestroy {

    belge: Belge;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private belgeService: BelgeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBelges();
    }

    load(id) {
        this.belgeService.find(id).subscribe((belge) => {
            this.belge = belge;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBelges() {
        this.eventSubscriber = this.eventManager.subscribe(
            'belgeListModification',
            (response) => this.load(this.belge.id)
        );
    }
}
