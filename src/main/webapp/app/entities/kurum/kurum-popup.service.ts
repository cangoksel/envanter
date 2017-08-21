import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Kurum } from './kurum.model';
import { KurumService } from './kurum.service';

@Injectable()
export class KurumPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private kurumService: KurumService

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
                this.kurumService.find(id).subscribe((kurum) => {
                    if (kurum.yil) {
                        kurum.yil = {
                            year: kurum.yil.getFullYear(),
                            month: kurum.yil.getMonth() + 1,
                            day: kurum.yil.getDate()
                        };
                    }
                    this.ngbModalRef = this.kurumModalRef(component, kurum);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.kurumModalRef(component, new Kurum());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    kurumModalRef(component: Component, kurum: Kurum): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.kurum = kurum;
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
