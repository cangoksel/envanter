import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TibbiCihazTehlikeSinifi } from './tibbi-cihaz-tehlike-sinifi.model';
import { TibbiCihazTehlikeSinifiService } from './tibbi-cihaz-tehlike-sinifi.service';

@Injectable()
export class TibbiCihazTehlikeSinifiPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private tibbiCihazTehlikeSinifiService: TibbiCihazTehlikeSinifiService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.tibbiCihazTehlikeSinifiService.find(id).subscribe((tibbiCihazTehlikeSinifi) => {
                    this.ngbModalRef = this.tibbiCihazTehlikeSinifiModalRef(component, tibbiCihazTehlikeSinifi);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.tibbiCihazTehlikeSinifiModalRef(component, new TibbiCihazTehlikeSinifi());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    tibbiCihazTehlikeSinifiModalRef(component: Component, tibbiCihazTehlikeSinifi: TibbiCihazTehlikeSinifi): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tibbiCihazTehlikeSinifi = tibbiCihazTehlikeSinifi;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
