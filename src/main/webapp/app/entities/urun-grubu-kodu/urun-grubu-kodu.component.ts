import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { UrunGrubuKodu } from './urun-grubu-kodu.model';
import { UrunGrubuKoduService } from './urun-grubu-kodu.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-urun-grubu-kodu',
    templateUrl: './urun-grubu-kodu.component.html'
})
export class UrunGrubuKoduComponent implements OnInit, OnDestroy {
urunGrubuKodus: UrunGrubuKodu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private urunGrubuKoduService: UrunGrubuKoduService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.urunGrubuKoduService.query().subscribe(
            (res: ResponseWrapper) => {
                this.urunGrubuKodus = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUrunGrubuKodus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UrunGrubuKodu) {
        return item.id;
    }
    registerChangeInUrunGrubuKodus() {
        this.eventSubscriber = this.eventManager.subscribe('urunGrubuKoduListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
