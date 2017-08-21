import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { IsyeriBilgileri } from './isyeri-bilgileri.model';
import { IsyeriBilgileriService } from './isyeri-bilgileri.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-isyeri-bilgileri',
    templateUrl: './isyeri-bilgileri.component.html'
})
export class IsyeriBilgileriComponent implements OnInit, OnDestroy {
isyeriBilgileris: IsyeriBilgileri[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private isyeriBilgileriService: IsyeriBilgileriService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.isyeriBilgileriService.query().subscribe(
            (res: ResponseWrapper) => {
                this.isyeriBilgileris = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInIsyeriBilgileris();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IsyeriBilgileri) {
        return item.id;
    }
    registerChangeInIsyeriBilgileris() {
        this.eventSubscriber = this.eventManager.subscribe('isyeriBilgileriListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
