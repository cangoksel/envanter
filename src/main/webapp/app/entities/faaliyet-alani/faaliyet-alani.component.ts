import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { FaaliyetAlani } from './faaliyet-alani.model';
import { FaaliyetAlaniService } from './faaliyet-alani.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-faaliyet-alani',
    templateUrl: './faaliyet-alani.component.html'
})
export class FaaliyetAlaniComponent implements OnInit, OnDestroy {
faaliyetAlanis: FaaliyetAlani[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private faaliyetAlaniService: FaaliyetAlaniService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.faaliyetAlaniService.query().subscribe(
            (res: ResponseWrapper) => {
                this.faaliyetAlanis = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFaaliyetAlanis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FaaliyetAlani) {
        return item.id;
    }
    registerChangeInFaaliyetAlanis() {
        this.eventSubscriber = this.eventManager.subscribe('faaliyetAlaniListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
