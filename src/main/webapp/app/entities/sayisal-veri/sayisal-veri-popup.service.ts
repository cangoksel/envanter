import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SayisalVeri } from './sayisal-veri.model';
import { SayisalVeriService } from './sayisal-veri.service';

@Injectable()
export class SayisalVeriPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private sayisalVeriService: SayisalVeriService

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
                this.sayisalVeriService.find(id).subscribe((sayisalVeri) => {
                    if (sayisalVeri.yil) {
                        sayisalVeri.yil = {
                            year: sayisalVeri.yil.getFullYear(),
                            month: sayisalVeri.yil.getMonth() + 1,
                            day: sayisalVeri.yil.getDate()
                        };
                    }
                    this.ngbModalRef = this.sayisalVeriModalRef(component, sayisalVeri);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.sayisalVeriModalRef(component, new SayisalVeri());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    sayisalVeriModalRef(component: Component, sayisalVeri: SayisalVeri): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sayisalVeri = sayisalVeri;
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
