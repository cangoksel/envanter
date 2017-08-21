import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { UretimBilgisi } from './uretim-bilgisi.model';
import { UretimBilgisiService } from './uretim-bilgisi.service';

@Component({
    selector: 'jhi-uretim-bilgisi-detail',
    templateUrl: './uretim-bilgisi-detail.component.html'
})
export class UretimBilgisiDetailComponent implements OnInit, OnDestroy {

    uretimBilgisi: UretimBilgisi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private uretimBilgisiService: UretimBilgisiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUretimBilgisis();
    }

    load(id) {
        this.uretimBilgisiService.find(id).subscribe((uretimBilgisi) => {
            this.uretimBilgisi = uretimBilgisi;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUretimBilgisis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'uretimBilgisiListModification',
            (response) => this.load(this.uretimBilgisi.id)
        );
    }
}
