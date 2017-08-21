import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { UrunGrubuKodu } from './urun-grubu-kodu.model';
import { UrunGrubuKoduService } from './urun-grubu-kodu.service';

@Injectable()
export class UrunGrubuKoduPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private urunGrubuKoduService: UrunGrubuKoduService

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
                this.urunGrubuKoduService.find(id).subscribe((urunGrubuKodu) => {
                    this.ngbModalRef = this.urunGrubuKoduModalRef(component, urunGrubuKodu);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.urunGrubuKoduModalRef(component, new UrunGrubuKodu());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    urunGrubuKoduModalRef(component: Component, urunGrubuKodu: UrunGrubuKodu): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.urunGrubuKodu = urunGrubuKodu;
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
