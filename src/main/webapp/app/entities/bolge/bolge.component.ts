import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Bolge } from './bolge.model';
import { BolgeService } from './bolge.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-bolge',
    templateUrl: './bolge.component.html'
})
export class BolgeComponent implements OnInit, OnDestroy {
bolges: Bolge[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bolgeService: BolgeService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bolgeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.bolges = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBolges();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Bolge) {
        return item.id;
    }
    registerChangeInBolges() {
        this.eventSubscriber = this.eventManager.subscribe('bolgeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
