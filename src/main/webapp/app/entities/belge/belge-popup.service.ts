import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Belge } from './belge.model';
import { BelgeService } from './belge.service';

@Injectable()
export class BelgePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private belgeService: BelgeService

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
                this.belgeService.find(id).subscribe((belge) => {
                    if (belge.olusturmaZamani) {
                        belge.olusturmaZamani = {
                            year: belge.olusturmaZamani.getFullYear(),
                            month: belge.olusturmaZamani.getMonth() + 1,
                            day: belge.olusturmaZamani.getDate()
                        };
                    }
                    this.ngbModalRef = this.belgeModalRef(component, belge);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.belgeModalRef(component, new Belge());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    belgeModalRef(component: Component, belge: Belge): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.belge = belge;
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
