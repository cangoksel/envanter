import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ProjeBilgisi } from './proje-bilgisi.model';
import { ProjeBilgisiService } from './proje-bilgisi.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-proje-bilgisi',
    templateUrl: './proje-bilgisi.component.html'
})
export class ProjeBilgisiComponent implements OnInit, OnDestroy {
projeBilgisis: ProjeBilgisi[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private projeBilgisiService: ProjeBilgisiService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.projeBilgisiService.query().subscribe(
            (res: ResponseWrapper) => {
                this.projeBilgisis = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProjeBilgisis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ProjeBilgisi) {
        return item.id;
    }
    registerChangeInProjeBilgisis() {
        this.eventSubscriber = this.eventManager.subscribe('projeBilgisiListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
