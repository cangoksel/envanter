import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Kisi } from './kisi.model';
import { KisiService } from './kisi.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-kisi',
    templateUrl: './kisi.component.html'
})
export class KisiComponent implements OnInit, OnDestroy {
kisis: Kisi[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private kisiService: KisiService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.kisiService.query().subscribe(
            (res: ResponseWrapper) => {
                this.kisis = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInKisis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Kisi) {
        return item.id;
    }
    registerChangeInKisis() {
        this.eventSubscriber = this.eventManager.subscribe('kisiListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
