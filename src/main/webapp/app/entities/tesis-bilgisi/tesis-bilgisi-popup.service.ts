import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TesisBilgisi } from './tesis-bilgisi.model';
import { TesisBilgisiService } from './tesis-bilgisi.service';

@Injectable()
export class TesisBilgisiPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private tesisBilgisiService: TesisBilgisiService

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
                this.tesisBilgisiService.find(id).subscribe((tesisBilgisi) => {
                    this.ngbModalRef = this.tesisBilgisiModalRef(component, tesisBilgisi);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.tesisBilgisiModalRef(component, new TesisBilgisi());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    tesisBilgisiModalRef(component: Component, tesisBilgisi: TesisBilgisi): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tesisBilgisi = tesisBilgisi;
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
