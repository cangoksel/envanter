import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ActKodu } from './act-kodu.model';
import { ActKoduService } from './act-kodu.service';

@Component({
    selector: 'jhi-act-kodu-detail',
    templateUrl: './act-kodu-detail.component.html'
})
export class ActKoduDetailComponent implements OnInit, OnDestroy {

    actKodu: ActKodu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private actKoduService: ActKoduService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInActKodus();
    }

    load(id) {
        this.actKoduService.find(id).subscribe((actKodu) => {
            this.actKodu = actKodu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInActKodus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'actKoduListModification',
            (response) => this.load(this.actKodu.id)
        );
    }
}
