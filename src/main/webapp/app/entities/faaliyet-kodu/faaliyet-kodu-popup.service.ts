import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FaaliyetKodu } from './faaliyet-kodu.model';
import { FaaliyetKoduService } from './faaliyet-kodu.service';

@Injectable()
export class FaaliyetKoduPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private faaliyetKoduService: FaaliyetKoduService

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
                this.faaliyetKoduService.find(id).subscribe((faaliyetKodu) => {
                    this.ngbModalRef = this.faaliyetKoduModalRef(component, faaliyetKodu);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.faaliyetKoduModalRef(component, new FaaliyetKodu());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    faaliyetKoduModalRef(component: Component, faaliyetKodu: FaaliyetKodu): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.faaliyetKodu = faaliyetKodu;
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
