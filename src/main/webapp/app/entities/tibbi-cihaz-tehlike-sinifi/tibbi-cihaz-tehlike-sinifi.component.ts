import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { TibbiCihazTehlikeSinifi } from './tibbi-cihaz-tehlike-sinifi.model';
import { TibbiCihazTehlikeSinifiService } from './tibbi-cihaz-tehlike-sinifi.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-tibbi-cihaz-tehlike-sinifi',
    templateUrl: './tibbi-cihaz-tehlike-sinifi.component.html'
})
export class TibbiCihazTehlikeSinifiComponent implements OnInit, OnDestroy {
tibbiCihazTehlikeSinifis: TibbiCihazTehlikeSinifi[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tibbiCihazTehlikeSinifiService: TibbiCihazTehlikeSinifiService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tibbiCihazTehlikeSinifiService.query().subscribe(
            (res: ResponseWrapper) => {
                this.tibbiCihazTehlikeSinifis = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTibbiCihazTehlikeSinifis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TibbiCihazTehlikeSinifi) {
        return item.id;
    }
    registerChangeInTibbiCihazTehlikeSinifis() {
        this.eventSubscriber = this.eventManager.subscribe('tibbiCihazTehlikeSinifiListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
