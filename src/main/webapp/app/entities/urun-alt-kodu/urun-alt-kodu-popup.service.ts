import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { UrunAltKodu } from './urun-alt-kodu.model';
import { UrunAltKoduService } from './urun-alt-kodu.service';

@Injectable()
export class UrunAltKoduPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private urunAltKoduService: UrunAltKoduService

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
                this.urunAltKoduService.find(id).subscribe((urunAltKodu) => {
                    this.ngbModalRef = this.urunAltKoduModalRef(component, urunAltKodu);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.urunAltKoduModalRef(component, new UrunAltKodu());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    urunAltKoduModalRef(component: Component, urunAltKodu: UrunAltKodu): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.urunAltKodu = urunAltKodu;
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
