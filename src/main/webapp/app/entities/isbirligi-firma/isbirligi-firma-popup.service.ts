import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IsbirligiFirma } from './isbirligi-firma.model';
import { IsbirligiFirmaService } from './isbirligi-firma.service';

@Injectable()
export class IsbirligiFirmaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private isbirligiFirmaService: IsbirligiFirmaService

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
                this.isbirligiFirmaService.find(id).subscribe((isbirligiFirma) => {
                    this.ngbModalRef = this.isbirligiFirmaModalRef(component, isbirligiFirma);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.isbirligiFirmaModalRef(component, new IsbirligiFirma());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    isbirligiFirmaModalRef(component: Component, isbirligiFirma: IsbirligiFirma): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.isbirligiFirma = isbirligiFirma;
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
