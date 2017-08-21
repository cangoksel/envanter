import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CalisanTipi } from './calisan-tipi.model';
import { CalisanTipiService } from './calisan-tipi.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-calisan-tipi',
    templateUrl: './calisan-tipi.component.html'
})
export class CalisanTipiComponent implements OnInit, OnDestroy {
calisanTipis: CalisanTipi[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private calisanTipiService: CalisanTipiService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.calisanTipiService.query().subscribe(
            (res: ResponseWrapper) => {
                this.calisanTipis = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCalisanTipis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CalisanTipi) {
        return item.id;
    }
    registerChangeInCalisanTipis() {
        this.eventSubscriber = this.eventManager.subscribe('calisanTipiListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
