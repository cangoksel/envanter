import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { OrtaklikBilgileri } from './ortaklik-bilgileri.model';
import { OrtaklikBilgileriService } from './ortaklik-bilgileri.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-ortaklik-bilgileri',
    templateUrl: './ortaklik-bilgileri.component.html'
})
export class OrtaklikBilgileriComponent implements OnInit, OnDestroy {
ortaklikBilgileris: OrtaklikBilgileri[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ortaklikBilgileriService: OrtaklikBilgileriService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ortaklikBilgileriService.query().subscribe(
            (res: ResponseWrapper) => {
                this.ortaklikBilgileris = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInOrtaklikBilgileris();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: OrtaklikBilgileri) {
        return item.id;
    }
    registerChangeInOrtaklikBilgileris() {
        this.eventSubscriber = this.eventManager.subscribe('ortaklikBilgileriListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
