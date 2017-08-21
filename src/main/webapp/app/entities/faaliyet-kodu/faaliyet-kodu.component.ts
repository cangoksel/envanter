import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { FaaliyetKodu } from './faaliyet-kodu.model';
import { FaaliyetKoduService } from './faaliyet-kodu.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-faaliyet-kodu',
    templateUrl: './faaliyet-kodu.component.html'
})
export class FaaliyetKoduComponent implements OnInit, OnDestroy {
faaliyetKodus: FaaliyetKodu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private faaliyetKoduService: FaaliyetKoduService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.faaliyetKoduService.query().subscribe(
            (res: ResponseWrapper) => {
                this.faaliyetKodus = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFaaliyetKodus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FaaliyetKodu) {
        return item.id;
    }
    registerChangeInFaaliyetKodus() {
        this.eventSubscriber = this.eventManager.subscribe('faaliyetKoduListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
