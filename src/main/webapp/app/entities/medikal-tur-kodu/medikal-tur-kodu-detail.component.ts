import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { MedikalTurKodu } from './medikal-tur-kodu.model';
import { MedikalTurKoduService } from './medikal-tur-kodu.service';

@Component({
    selector: 'jhi-medikal-tur-kodu-detail',
    templateUrl: './medikal-tur-kodu-detail.component.html'
})
export class MedikalTurKoduDetailComponent implements OnInit, OnDestroy {

    medikalTurKodu: MedikalTurKodu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private medikalTurKoduService: MedikalTurKoduService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMedikalTurKodus();
    }

    load(id) {
        this.medikalTurKoduService.find(id).subscribe((medikalTurKodu) => {
            this.medikalTurKodu = medikalTurKodu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMedikalTurKodus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'medikalTurKoduListModification',
            (response) => this.load(this.medikalTurKodu.id)
        );
    }
}
