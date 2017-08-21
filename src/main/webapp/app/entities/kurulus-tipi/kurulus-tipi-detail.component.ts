import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { KurulusTipi } from './kurulus-tipi.model';
import { KurulusTipiService } from './kurulus-tipi.service';

@Component({
    selector: 'jhi-kurulus-tipi-detail',
    templateUrl: './kurulus-tipi-detail.component.html'
})
export class KurulusTipiDetailComponent implements OnInit, OnDestroy {

    kurulusTipi: KurulusTipi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private kurulusTipiService: KurulusTipiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKurulusTipis();
    }

    load(id) {
        this.kurulusTipiService.find(id).subscribe((kurulusTipi) => {
            this.kurulusTipi = kurulusTipi;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKurulusTipis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'kurulusTipiListModification',
            (response) => this.load(this.kurulusTipi.id)
        );
    }
}
