import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CalisanSayiBilgisi } from './calisan-sayi-bilgisi.model';
import { CalisanSayiBilgisiService } from './calisan-sayi-bilgisi.service';

@Component({
    selector: 'jhi-calisan-sayi-bilgisi-detail',
    templateUrl: './calisan-sayi-bilgisi-detail.component.html'
})
export class CalisanSayiBilgisiDetailComponent implements OnInit, OnDestroy {

    calisanSayiBilgisi: CalisanSayiBilgisi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private calisanSayiBilgisiService: CalisanSayiBilgisiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCalisanSayiBilgisis();
    }

    load(id) {
        this.calisanSayiBilgisiService.find(id).subscribe((calisanSayiBilgisi) => {
            this.calisanSayiBilgisi = calisanSayiBilgisi;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCalisanSayiBilgisis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'calisanSayiBilgisiListModification',
            (response) => this.load(this.calisanSayiBilgisi.id)
        );
    }
}
