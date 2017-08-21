import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { YillikCiro } from './yillik-ciro.model';
import { YillikCiroService } from './yillik-ciro.service';

@Injectable()
export class YillikCiroPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private yillikCiroService: YillikCiroService

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
                this.yillikCiroService.find(id).subscribe((yillikCiro) => {
                    if (yillikCiro.yil) {
                        yillikCiro.yil = {
                            year: yillikCiro.yil.getFullYear(),
                            month: yillikCiro.yil.getMonth() + 1,
                            day: yillikCiro.yil.getDate()
                        };
                    }
                    this.ngbModalRef = this.yillikCiroModalRef(component, yillikCiro);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.yillikCiroModalRef(component, new YillikCiro());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    yillikCiroModalRef(component: Component, yillikCiro: YillikCiro): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.yillikCiro = yillikCiro;
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
