import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Ihracat } from './ihracat.model';
import { IhracatService } from './ihracat.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-ihracat',
    templateUrl: './ihracat.component.html'
})
export class IhracatComponent implements OnInit, OnDestroy {
ihracats: Ihracat[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ihracatService: IhracatService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ihracatService.query().subscribe(
            (res: ResponseWrapper) => {
                this.ihracats = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInIhracats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Ihracat) {
        return item.id;
    }
    registerChangeInIhracats() {
        this.eventSubscriber = this.eventManager.subscribe('ihracatListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
