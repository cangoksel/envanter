import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { UrunGrubu } from './urun-grubu.model';
import { UrunGrubuService } from './urun-grubu.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-urun-grubu',
    templateUrl: './urun-grubu.component.html'
})
export class UrunGrubuComponent implements OnInit, OnDestroy {
urunGrubus: UrunGrubu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private urunGrubuService: UrunGrubuService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.urunGrubuService.query().subscribe(
            (res: ResponseWrapper) => {
                this.urunGrubus = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUrunGrubus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UrunGrubu) {
        return item.id;
    }
    registerChangeInUrunGrubus() {
        this.eventSubscriber = this.eventManager.subscribe('urunGrubuListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
