import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ProjeBilgisi } from './proje-bilgisi.model';
import { ProjeBilgisiService } from './proje-bilgisi.service';

@Component({
    selector: 'jhi-proje-bilgisi-detail',
    templateUrl: './proje-bilgisi-detail.component.html'
})
export class ProjeBilgisiDetailComponent implements OnInit, OnDestroy {

    projeBilgisi: ProjeBilgisi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private projeBilgisiService: ProjeBilgisiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProjeBilgisis();
    }

    load(id) {
        this.projeBilgisiService.find(id).subscribe((projeBilgisi) => {
            this.projeBilgisi = projeBilgisi;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProjeBilgisis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'projeBilgisiListModification',
            (response) => this.load(this.projeBilgisi.id)
        );
    }
}
