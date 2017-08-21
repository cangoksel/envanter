import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { IsyeriBilgileri } from './isyeri-bilgileri.model';
import { IsyeriBilgileriService } from './isyeri-bilgileri.service';

@Component({
    selector: 'jhi-isyeri-bilgileri-detail',
    templateUrl: './isyeri-bilgileri-detail.component.html'
})
export class IsyeriBilgileriDetailComponent implements OnInit, OnDestroy {

    isyeriBilgileri: IsyeriBilgileri;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private isyeriBilgileriService: IsyeriBilgileriService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIsyeriBilgileris();
    }

    load(id) {
        this.isyeriBilgileriService.find(id).subscribe((isyeriBilgileri) => {
            this.isyeriBilgileri = isyeriBilgileri;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIsyeriBilgileris() {
        this.eventSubscriber = this.eventManager.subscribe(
            'isyeriBilgileriListModification',
            (response) => this.load(this.isyeriBilgileri.id)
        );
    }
}
