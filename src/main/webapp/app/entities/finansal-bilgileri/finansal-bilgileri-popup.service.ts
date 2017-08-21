import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FinansalBilgileri } from './finansal-bilgileri.model';
import { FinansalBilgileriService } from './finansal-bilgileri.service';

@Injectable()
export class FinansalBilgileriPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private finansalBilgileriService: FinansalBilgileriService

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
                this.finansalBilgileriService.find(id).subscribe((finansalBilgileri) => {
                    if (finansalBilgileri.yil) {
                        finansalBilgileri.yil = {
                            year: finansalBilgileri.yil.getFullYear(),
                            month: finansalBilgileri.yil.getMonth() + 1,
                            day: finansalBilgileri.yil.getDate()
                        };
                    }
                    this.ngbModalRef = this.finansalBilgileriModalRef(component, finansalBilgileri);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.finansalBilgileriModalRef(component, new FinansalBilgileri());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    finansalBilgileriModalRef(component: Component, finansalBilgileri: FinansalBilgileri): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.finansalBilgileri = finansalBilgileri;
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
