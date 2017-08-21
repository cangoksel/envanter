import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CalisanSayiBilgisi } from './calisan-sayi-bilgisi.model';
import { CalisanSayiBilgisiService } from './calisan-sayi-bilgisi.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-calisan-sayi-bilgisi',
    templateUrl: './calisan-sayi-bilgisi.component.html'
})
export class CalisanSayiBilgisiComponent implements OnInit, OnDestroy {
calisanSayiBilgisis: CalisanSayiBilgisi[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private calisanSayiBilgisiService: CalisanSayiBilgisiService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.calisanSayiBilgisiService.query().subscribe(
            (res: ResponseWrapper) => {
                this.calisanSayiBilgisis = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCalisanSayiBilgisis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CalisanSayiBilgisi) {
        return item.id;
    }
    registerChangeInCalisanSayiBilgisis() {
        this.eventSubscriber = this.eventManager.subscribe('calisanSayiBilgisiListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
