import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { UretimBilgisi } from './uretim-bilgisi.model';
import { UretimBilgisiService } from './uretim-bilgisi.service';

@Injectable()
export class UretimBilgisiPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private uretimBilgisiService: UretimBilgisiService

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
                this.uretimBilgisiService.find(id).subscribe((uretimBilgisi) => {
                    this.ngbModalRef = this.uretimBilgisiModalRef(component, uretimBilgisi);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.uretimBilgisiModalRef(component, new UretimBilgisi());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    uretimBilgisiModalRef(component: Component, uretimBilgisi: UretimBilgisi): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.uretimBilgisi = uretimBilgisi;
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
