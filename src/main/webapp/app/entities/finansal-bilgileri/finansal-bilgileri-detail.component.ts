import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { FinansalBilgileri } from './finansal-bilgileri.model';
import { FinansalBilgileriService } from './finansal-bilgileri.service';

@Component({
    selector: 'jhi-finansal-bilgileri-detail',
    templateUrl: './finansal-bilgileri-detail.component.html'
})
export class FinansalBilgileriDetailComponent implements OnInit, OnDestroy {

    finansalBilgileri: FinansalBilgileri;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private finansalBilgileriService: FinansalBilgileriService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFinansalBilgileris();
    }

    load(id) {
        this.finansalBilgileriService.find(id).subscribe((finansalBilgileri) => {
            this.finansalBilgileri = finansalBilgileri;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFinansalBilgileris() {
        this.eventSubscriber = this.eventManager.subscribe(
            'finansalBilgileriListModification',
            (response) => this.load(this.finansalBilgileri.id)
        );
    }
}
