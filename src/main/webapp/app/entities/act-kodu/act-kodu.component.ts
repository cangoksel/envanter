import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ActKodu } from './act-kodu.model';
import { ActKoduService } from './act-kodu.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-act-kodu',
    templateUrl: './act-kodu.component.html'
})
export class ActKoduComponent implements OnInit, OnDestroy {
actKodus: ActKodu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private actKoduService: ActKoduService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.actKoduService.query().subscribe(
            (res: ResponseWrapper) => {
                this.actKodus = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInActKodus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ActKodu) {
        return item.id;
    }
    registerChangeInActKodus() {
        this.eventSubscriber = this.eventManager.subscribe('actKoduListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
