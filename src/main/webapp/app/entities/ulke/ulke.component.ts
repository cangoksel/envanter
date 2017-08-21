import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Ulke } from './ulke.model';
import { UlkeService } from './ulke.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-ulke',
    templateUrl: './ulke.component.html'
})
export class UlkeComponent implements OnInit, OnDestroy {
ulkes: Ulke[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ulkeService: UlkeService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ulkeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.ulkes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUlkes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Ulke) {
        return item.id;
    }
    registerChangeInUlkes() {
        this.eventSubscriber = this.eventManager.subscribe('ulkeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
