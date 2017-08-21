import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { MkysKodu } from './mkys-kodu.model';
import { MkysKoduService } from './mkys-kodu.service';

@Component({
    selector: 'jhi-mkys-kodu-detail',
    templateUrl: './mkys-kodu-detail.component.html'
})
export class MkysKoduDetailComponent implements OnInit, OnDestroy {

    mkysKodu: MkysKodu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mkysKoduService: MkysKoduService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMkysKodus();
    }

    load(id) {
        this.mkysKoduService.find(id).subscribe((mkysKodu) => {
            this.mkysKodu = mkysKodu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMkysKodus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mkysKoduListModification',
            (response) => this.load(this.mkysKodu.id)
        );
    }
}
