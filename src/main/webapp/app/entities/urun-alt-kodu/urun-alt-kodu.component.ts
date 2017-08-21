import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { UrunAltKodu } from './urun-alt-kodu.model';
import { UrunAltKoduService } from './urun-alt-kodu.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-urun-alt-kodu',
    templateUrl: './urun-alt-kodu.component.html'
})
export class UrunAltKoduComponent implements OnInit, OnDestroy {
urunAltKodus: UrunAltKodu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private urunAltKoduService: UrunAltKoduService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.urunAltKoduService.query().subscribe(
            (res: ResponseWrapper) => {
                this.urunAltKodus = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUrunAltKodus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UrunAltKodu) {
        return item.id;
    }
    registerChangeInUrunAltKodus() {
        this.eventSubscriber = this.eventManager.subscribe('urunAltKoduListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
