import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { FaaliyetKodu } from './faaliyet-kodu.model';
import { FaaliyetKoduService } from './faaliyet-kodu.service';

@Component({
    selector: 'jhi-faaliyet-kodu-detail',
    templateUrl: './faaliyet-kodu-detail.component.html'
})
export class FaaliyetKoduDetailComponent implements OnInit, OnDestroy {

    faaliyetKodu: FaaliyetKodu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private faaliyetKoduService: FaaliyetKoduService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFaaliyetKodus();
    }

    load(id) {
        this.faaliyetKoduService.find(id).subscribe((faaliyetKodu) => {
            this.faaliyetKodu = faaliyetKodu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFaaliyetKodus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'faaliyetKoduListModification',
            (response) => this.load(this.faaliyetKodu.id)
        );
    }
}
