import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { BelgeTipi } from './belge-tipi.model';
import { BelgeTipiService } from './belge-tipi.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-belge-tipi',
    templateUrl: './belge-tipi.component.html'
})
export class BelgeTipiComponent implements OnInit, OnDestroy {
belgeTipis: BelgeTipi[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private belgeTipiService: BelgeTipiService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.belgeTipiService.query().subscribe(
            (res: ResponseWrapper) => {
                this.belgeTipis = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBelgeTipis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BelgeTipi) {
        return item.id;
    }
    registerChangeInBelgeTipis() {
        this.eventSubscriber = this.eventManager.subscribe('belgeTipiListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
