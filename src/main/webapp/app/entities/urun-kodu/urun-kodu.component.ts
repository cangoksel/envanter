import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { UrunKodu } from './urun-kodu.model';
import { UrunKoduService } from './urun-kodu.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-urun-kodu',
    templateUrl: './urun-kodu.component.html'
})
export class UrunKoduComponent implements OnInit, OnDestroy {
urunKodus: UrunKodu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private urunKoduService: UrunKoduService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.urunKoduService.query().subscribe(
            (res: ResponseWrapper) => {
                this.urunKodus = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUrunKodus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UrunKodu) {
        return item.id;
    }
    registerChangeInUrunKodus() {
        this.eventSubscriber = this.eventManager.subscribe('urunKoduListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
