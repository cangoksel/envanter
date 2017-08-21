import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { GenelFirmaBilgileri } from './genel-firma-bilgileri.model';
import { GenelFirmaBilgileriService } from './genel-firma-bilgileri.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-genel-firma-bilgileri',
    templateUrl: './genel-firma-bilgileri.component.html'
})
export class GenelFirmaBilgileriComponent implements OnInit, OnDestroy {
genelFirmaBilgileris: GenelFirmaBilgileri[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private genelFirmaBilgileriService: GenelFirmaBilgileriService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.genelFirmaBilgileriService.query().subscribe(
            (res: ResponseWrapper) => {
                this.genelFirmaBilgileris = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGenelFirmaBilgileris();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: GenelFirmaBilgileri) {
        return item.id;
    }
    registerChangeInGenelFirmaBilgileris() {
        this.eventSubscriber = this.eventManager.subscribe('genelFirmaBilgileriListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
