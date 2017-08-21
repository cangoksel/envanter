import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MedikalTurKodu } from './medikal-tur-kodu.model';
import { MedikalTurKoduService } from './medikal-tur-kodu.service';

@Injectable()
export class MedikalTurKoduPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private medikalTurKoduService: MedikalTurKoduService

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
                this.medikalTurKoduService.find(id).subscribe((medikalTurKodu) => {
                    this.ngbModalRef = this.medikalTurKoduModalRef(component, medikalTurKodu);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.medikalTurKoduModalRef(component, new MedikalTurKodu());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    medikalTurKoduModalRef(component: Component, medikalTurKodu: MedikalTurKodu): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.medikalTurKodu = medikalTurKodu;
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
