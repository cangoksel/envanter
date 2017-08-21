import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { GtipKodu } from './gtip-kodu.model';
import { GtipKoduService } from './gtip-kodu.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-gtip-kodu',
    templateUrl: './gtip-kodu.component.html'
})
export class GtipKoduComponent implements OnInit, OnDestroy {
gtipKodus: GtipKodu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private gtipKoduService: GtipKoduService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.gtipKoduService.query().subscribe(
            (res: ResponseWrapper) => {
                this.gtipKodus = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGtipKodus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: GtipKodu) {
        return item.id;
    }
    registerChangeInGtipKodus() {
        this.eventSubscriber = this.eventManager.subscribe('gtipKoduListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
