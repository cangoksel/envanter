import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { GenelFirmaBilgileri } from './genel-firma-bilgileri.model';
import { GenelFirmaBilgileriService } from './genel-firma-bilgileri.service';

@Injectable()
export class GenelFirmaBilgileriPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private genelFirmaBilgileriService: GenelFirmaBilgileriService

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
                this.genelFirmaBilgileriService.find(id).subscribe((genelFirmaBilgileri) => {
                    if (genelFirmaBilgileri.kurulusTarihi) {
                        genelFirmaBilgileri.kurulusTarihi = {
                            year: genelFirmaBilgileri.kurulusTarihi.getFullYear(),
                            month: genelFirmaBilgileri.kurulusTarihi.getMonth() + 1,
                            day: genelFirmaBilgileri.kurulusTarihi.getDate()
                        };
                    }
                    this.ngbModalRef = this.genelFirmaBilgileriModalRef(component, genelFirmaBilgileri);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.genelFirmaBilgileriModalRef(component, new GenelFirmaBilgileri());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    genelFirmaBilgileriModalRef(component: Component, genelFirmaBilgileri: GenelFirmaBilgileri): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.genelFirmaBilgileri = genelFirmaBilgileri;
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
