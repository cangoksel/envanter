import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { NaceKodu } from './nace-kodu.model';
import { NaceKoduService } from './nace-kodu.service';

@Component({
    selector: 'jhi-nace-kodu-detail',
    templateUrl: './nace-kodu-detail.component.html'
})
export class NaceKoduDetailComponent implements OnInit, OnDestroy {

    naceKodu: NaceKodu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private naceKoduService: NaceKoduService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNaceKodus();
    }

    load(id) {
        this.naceKoduService.find(id).subscribe((naceKodu) => {
            this.naceKodu = naceKodu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNaceKodus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'naceKoduListModification',
            (response) => this.load(this.naceKodu.id)
        );
    }
}
