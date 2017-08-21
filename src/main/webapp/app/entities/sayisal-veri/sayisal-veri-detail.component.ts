import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { SayisalVeri } from './sayisal-veri.model';
import { SayisalVeriService } from './sayisal-veri.service';

@Component({
    selector: 'jhi-sayisal-veri-detail',
    templateUrl: './sayisal-veri-detail.component.html'
})
export class SayisalVeriDetailComponent implements OnInit, OnDestroy {

    sayisalVeri: SayisalVeri;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sayisalVeriService: SayisalVeriService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSayisalVeris();
    }

    load(id) {
        this.sayisalVeriService.find(id).subscribe((sayisalVeri) => {
            this.sayisalVeri = sayisalVeri;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSayisalVeris() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sayisalVeriListModification',
            (response) => this.load(this.sayisalVeri.id)
        );
    }
}
