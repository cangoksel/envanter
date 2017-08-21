import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { MkysKodu } from './mkys-kodu.model';
import { MkysKoduService } from './mkys-kodu.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-mkys-kodu',
    templateUrl: './mkys-kodu.component.html'
})
export class MkysKoduComponent implements OnInit, OnDestroy {
mkysKodus: MkysKodu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private mkysKoduService: MkysKoduService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.mkysKoduService.query().subscribe(
            (res: ResponseWrapper) => {
                this.mkysKodus = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMkysKodus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MkysKodu) {
        return item.id;
    }
    registerChangeInMkysKodus() {
        this.eventSubscriber = this.eventManager.subscribe('mkysKoduListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
