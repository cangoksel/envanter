import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProjeBilgisiComponent } from './proje-bilgisi.component';
import { ProjeBilgisiDetailComponent } from './proje-bilgisi-detail.component';
import { ProjeBilgisiPopupComponent } from './proje-bilgisi-dialog.component';
import { ProjeBilgisiDeletePopupComponent } from './proje-bilgisi-delete-dialog.component';

export const projeBilgisiRoute: Routes = [
    {
        path: 'proje-bilgisi',
        component: ProjeBilgisiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.projeBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'proje-bilgisi/:id',
        component: ProjeBilgisiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.projeBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projeBilgisiPopupRoute: Routes = [
    {
        path: 'proje-bilgisi-new',
        component: ProjeBilgisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.projeBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'proje-bilgisi/:id/edit',
        component: ProjeBilgisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.projeBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'proje-bilgisi/:id/delete',
        component: ProjeBilgisiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.projeBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
