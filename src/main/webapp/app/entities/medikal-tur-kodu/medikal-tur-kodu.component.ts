import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { MedikalTurKodu } from './medikal-tur-kodu.model';
import { MedikalTurKoduService } from './medikal-tur-kodu.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-medikal-tur-kodu',
    templateUrl: './medikal-tur-kodu.component.html'
})
export class MedikalTurKoduComponent implements OnInit, OnDestroy {
medikalTurKodus: MedikalTurKodu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private medikalTurKoduService: MedikalTurKoduService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.medikalTurKoduService.query().subscribe(
            (res: ResponseWrapper) => {
                this.medikalTurKodus = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMedikalTurKodus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MedikalTurKodu) {
        return item.id;
    }
    registerChangeInMedikalTurKodus() {
        this.eventSubscriber = this.eventManager.subscribe('medikalTurKoduListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
