import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { UrunGrubu } from './urun-grubu.model';
import { UrunGrubuService } from './urun-grubu.service';

@Component({
    selector: 'jhi-urun-grubu-detail',
    templateUrl: './urun-grubu-detail.component.html'
})
export class UrunGrubuDetailComponent implements OnInit, OnDestroy {

    urunGrubu: UrunGrubu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private urunGrubuService: UrunGrubuService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUrunGrubus();
    }

    load(id) {
        this.urunGrubuService.find(id).subscribe((urunGrubu) => {
            this.urunGrubu = urunGrubu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUrunGrubus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'urunGrubuListModification',
            (response) => this.load(this.urunGrubu.id)
        );
    }
}
