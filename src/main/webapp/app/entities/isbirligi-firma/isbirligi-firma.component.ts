import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { IsbirligiFirma } from './isbirligi-firma.model';
import { IsbirligiFirmaService } from './isbirligi-firma.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-isbirligi-firma',
    templateUrl: './isbirligi-firma.component.html'
})
export class IsbirligiFirmaComponent implements OnInit, OnDestroy {
isbirligiFirmas: IsbirligiFirma[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private isbirligiFirmaService: IsbirligiFirmaService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.isbirligiFirmaService.query().subscribe(
            (res: ResponseWrapper) => {
                this.isbirligiFirmas = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInIsbirligiFirmas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IsbirligiFirma) {
        return item.id;
    }
    registerChangeInIsbirligiFirmas() {
        this.eventSubscriber = this.eventManager.subscribe('isbirligiFirmaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
