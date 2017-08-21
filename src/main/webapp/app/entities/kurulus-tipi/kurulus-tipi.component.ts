import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { KurulusTipi } from './kurulus-tipi.model';
import { KurulusTipiService } from './kurulus-tipi.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-kurulus-tipi',
    templateUrl: './kurulus-tipi.component.html'
})
export class KurulusTipiComponent implements OnInit, OnDestroy {
kurulusTipis: KurulusTipi[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private kurulusTipiService: KurulusTipiService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.kurulusTipiService.query().subscribe(
            (res: ResponseWrapper) => {
                this.kurulusTipis = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInKurulusTipis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: KurulusTipi) {
        return item.id;
    }
    registerChangeInKurulusTipis() {
        this.eventSubscriber = this.eventManager.subscribe('kurulusTipiListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
