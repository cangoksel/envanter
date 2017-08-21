import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Kurulus } from './kurulus.model';
import { KurulusService } from './kurulus.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-kurulus',
    templateUrl: './kurulus.component.html'
})
export class KurulusComponent implements OnInit, OnDestroy {
kuruluses: Kurulus[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private kurulusService: KurulusService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.kurulusService.query().subscribe(
            (res: ResponseWrapper) => {
                this.kuruluses = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInKuruluses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Kurulus) {
        return item.id;
    }
    registerChangeInKuruluses() {
        this.eventSubscriber = this.eventManager.subscribe('kurulusListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
