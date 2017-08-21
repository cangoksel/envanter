import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { FinansalBilgileri } from './finansal-bilgileri.model';
import { FinansalBilgileriService } from './finansal-bilgileri.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-finansal-bilgileri',
    templateUrl: './finansal-bilgileri.component.html'
})
export class FinansalBilgileriComponent implements OnInit, OnDestroy {
finansalBilgileris: FinansalBilgileri[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private finansalBilgileriService: FinansalBilgileriService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.finansalBilgileriService.query().subscribe(
            (res: ResponseWrapper) => {
                this.finansalBilgileris = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFinansalBilgileris();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FinansalBilgileri) {
        return item.id;
    }
    registerChangeInFinansalBilgileris() {
        this.eventSubscriber = this.eventManager.subscribe('finansalBilgileriListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
