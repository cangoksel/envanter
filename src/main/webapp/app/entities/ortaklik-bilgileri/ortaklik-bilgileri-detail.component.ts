import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { OrtaklikBilgileri } from './ortaklik-bilgileri.model';
import { OrtaklikBilgileriService } from './ortaklik-bilgileri.service';

@Component({
    selector: 'jhi-ortaklik-bilgileri-detail',
    templateUrl: './ortaklik-bilgileri-detail.component.html'
})
export class OrtaklikBilgileriDetailComponent implements OnInit, OnDestroy {

    ortaklikBilgileri: OrtaklikBilgileri;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ortaklikBilgileriService: OrtaklikBilgileriService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrtaklikBilgileris();
    }

    load(id) {
        this.ortaklikBilgileriService.find(id).subscribe((ortaklikBilgileri) => {
            this.ortaklikBilgileri = ortaklikBilgileri;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrtaklikBilgileris() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ortaklikBilgileriListModification',
            (response) => this.load(this.ortaklikBilgileri.id)
        );
    }
}
