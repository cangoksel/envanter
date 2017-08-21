import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Kurum } from './kurum.model';
import { KurumService } from './kurum.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-kurum',
    templateUrl: './kurum.component.html'
})
export class KurumComponent implements OnInit, OnDestroy {
kurums: Kurum[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private kurumService: KurumService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.kurumService.query().subscribe(
            (res: ResponseWrapper) => {
                this.kurums = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInKurums();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Kurum) {
        return item.id;
    }
    registerChangeInKurums() {
        this.eventSubscriber = this.eventManager.subscribe('kurumListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
