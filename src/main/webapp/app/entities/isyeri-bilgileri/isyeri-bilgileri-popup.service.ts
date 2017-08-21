import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IsyeriBilgileri } from './isyeri-bilgileri.model';
import { IsyeriBilgileriService } from './isyeri-bilgileri.service';

@Injectable()
export class IsyeriBilgileriPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private isyeriBilgileriService: IsyeriBilgileriService

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
                this.isyeriBilgileriService.find(id).subscribe((isyeriBilgileri) => {
                    this.ngbModalRef = this.isyeriBilgileriModalRef(component, isyeriBilgileri);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.isyeriBilgileriModalRef(component, new IsyeriBilgileri());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    isyeriBilgileriModalRef(component: Component, isyeriBilgileri: IsyeriBilgileri): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.isyeriBilgileri = isyeriBilgileri;
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
