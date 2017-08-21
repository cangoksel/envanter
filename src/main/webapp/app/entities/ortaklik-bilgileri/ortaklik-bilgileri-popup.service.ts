import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OrtaklikBilgileri } from './ortaklik-bilgileri.model';
import { OrtaklikBilgileriService } from './ortaklik-bilgileri.service';

@Injectable()
export class OrtaklikBilgileriPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ortaklikBilgileriService: OrtaklikBilgileriService

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
                this.ortaklikBilgileriService.find(id).subscribe((ortaklikBilgileri) => {
                    this.ngbModalRef = this.ortaklikBilgileriModalRef(component, ortaklikBilgileri);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ortaklikBilgileriModalRef(component, new OrtaklikBilgileri());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ortaklikBilgileriModalRef(component: Component, ortaklikBilgileri: OrtaklikBilgileri): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ortaklikBilgileri = ortaklikBilgileri;
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
