import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { YillikCiro } from './yillik-ciro.model';
import { YillikCiroService } from './yillik-ciro.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-yillik-ciro',
    templateUrl: './yillik-ciro.component.html'
})
export class YillikCiroComponent implements OnInit, OnDestroy {
yillikCiros: YillikCiro[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private yillikCiroService: YillikCiroService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.yillikCiroService.query().subscribe(
            (res: ResponseWrapper) => {
                this.yillikCiros = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInYillikCiros();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: YillikCiro) {
        return item.id;
    }
    registerChangeInYillikCiros() {
        this.eventSubscriber = this.eventManager.subscribe('yillikCiroListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
