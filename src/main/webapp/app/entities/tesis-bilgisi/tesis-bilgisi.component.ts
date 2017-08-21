import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { TesisBilgisi } from './tesis-bilgisi.model';
import { TesisBilgisiService } from './tesis-bilgisi.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-tesis-bilgisi',
    templateUrl: './tesis-bilgisi.component.html'
})
export class TesisBilgisiComponent implements OnInit, OnDestroy {
tesisBilgisis: TesisBilgisi[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tesisBilgisiService: TesisBilgisiService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tesisBilgisiService.query().subscribe(
            (res: ResponseWrapper) => {
                this.tesisBilgisis = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTesisBilgisis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TesisBilgisi) {
        return item.id;
    }
    registerChangeInTesisBilgisis() {
        this.eventSubscriber = this.eventManager.subscribe('tesisBilgisiListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
