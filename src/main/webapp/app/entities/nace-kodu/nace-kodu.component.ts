import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { NaceKodu } from './nace-kodu.model';
import { NaceKoduService } from './nace-kodu.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-nace-kodu',
    templateUrl: './nace-kodu.component.html'
})
export class NaceKoduComponent implements OnInit, OnDestroy {
naceKodus: NaceKodu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private naceKoduService: NaceKoduService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.naceKoduService.query().subscribe(
            (res: ResponseWrapper) => {
                this.naceKodus = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNaceKodus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: NaceKodu) {
        return item.id;
    }
    registerChangeInNaceKodus() {
        this.eventSubscriber = this.eventManager.subscribe('naceKoduListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
