import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CalisanTipi } from './calisan-tipi.model';
import { CalisanTipiService } from './calisan-tipi.service';

@Component({
    selector: 'jhi-calisan-tipi-detail',
    templateUrl: './calisan-tipi-detail.component.html'
})
export class CalisanTipiDetailComponent implements OnInit, OnDestroy {

    calisanTipi: CalisanTipi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private calisanTipiService: CalisanTipiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCalisanTipis();
    }

    load(id) {
        this.calisanTipiService.find(id).subscribe((calisanTipi) => {
            this.calisanTipi = calisanTipi;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCalisanTipis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'calisanTipiListModification',
            (response) => this.load(this.calisanTipi.id)
        );
    }
}
