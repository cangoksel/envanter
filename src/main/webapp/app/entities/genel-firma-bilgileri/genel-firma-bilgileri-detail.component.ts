import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { GenelFirmaBilgileri } from './genel-firma-bilgileri.model';
import { GenelFirmaBilgileriService } from './genel-firma-bilgileri.service';

@Component({
    selector: 'jhi-genel-firma-bilgileri-detail',
    templateUrl: './genel-firma-bilgileri-detail.component.html'
})
export class GenelFirmaBilgileriDetailComponent implements OnInit, OnDestroy {

    genelFirmaBilgileri: GenelFirmaBilgileri;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private genelFirmaBilgileriService: GenelFirmaBilgileriService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGenelFirmaBilgileris();
    }

    load(id) {
        this.genelFirmaBilgileriService.find(id).subscribe((genelFirmaBilgileri) => {
            this.genelFirmaBilgileri = genelFirmaBilgileri;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGenelFirmaBilgileris() {
        this.eventSubscriber = this.eventManager.subscribe(
            'genelFirmaBilgileriListModification',
            (response) => this.load(this.genelFirmaBilgileri.id)
        );
    }
}
