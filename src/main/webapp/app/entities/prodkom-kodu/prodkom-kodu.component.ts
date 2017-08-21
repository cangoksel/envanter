import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ProdkomKodu } from './prodkom-kodu.model';
import { ProdkomKoduService } from './prodkom-kodu.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-prodkom-kodu',
    templateUrl: './prodkom-kodu.component.html'
})
export class ProdkomKoduComponent implements OnInit, OnDestroy {
prodkomKodus: ProdkomKodu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private prodkomKoduService: ProdkomKoduService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.prodkomKoduService.query().subscribe(
            (res: ResponseWrapper) => {
                this.prodkomKodus = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProdkomKodus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ProdkomKodu) {
        return item.id;
    }
    registerChangeInProdkomKodus() {
        this.eventSubscriber = this.eventManager.subscribe('prodkomKoduListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
