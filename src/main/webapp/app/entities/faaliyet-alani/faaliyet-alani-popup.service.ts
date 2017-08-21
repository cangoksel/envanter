import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FaaliyetAlani } from './faaliyet-alani.model';
import { FaaliyetAlaniService } from './faaliyet-alani.service';

@Injectable()
export class FaaliyetAlaniPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private faaliyetAlaniService: FaaliyetAlaniService

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
                this.faaliyetAlaniService.find(id).subscribe((faaliyetAlani) => {
                    this.ngbModalRef = this.faaliyetAlaniModalRef(component, faaliyetAlani);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.faaliyetAlaniModalRef(component, new FaaliyetAlani());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    faaliyetAlaniModalRef(component: Component, faaliyetAlani: FaaliyetAlani): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.faaliyetAlani = faaliyetAlani;
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
