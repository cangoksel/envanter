import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CalisanSayiBilgisi } from './calisan-sayi-bilgisi.model';
import { CalisanSayiBilgisiService } from './calisan-sayi-bilgisi.service';

@Injectable()
export class CalisanSayiBilgisiPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private calisanSayiBilgisiService: CalisanSayiBilgisiService

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
                this.calisanSayiBilgisiService.find(id).subscribe((calisanSayiBilgisi) => {
                    this.ngbModalRef = this.calisanSayiBilgisiModalRef(component, calisanSayiBilgisi);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.calisanSayiBilgisiModalRef(component, new CalisanSayiBilgisi());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    calisanSayiBilgisiModalRef(component: Component, calisanSayiBilgisi: CalisanSayiBilgisi): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.calisanSayiBilgisi = calisanSayiBilgisi;
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
