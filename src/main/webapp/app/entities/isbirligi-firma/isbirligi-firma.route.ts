import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { IsbirligiFirmaComponent } from './isbirligi-firma.component';
import { IsbirligiFirmaDetailComponent } from './isbirligi-firma-detail.component';
import { IsbirligiFirmaPopupComponent } from './isbirligi-firma-dialog.component';
import { IsbirligiFirmaDeletePopupComponent } from './isbirligi-firma-delete-dialog.component';

export const isbirligiFirmaRoute: Routes = [
    {
        path: 'isbirligi-firma',
        component: IsbirligiFirmaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.isbirligiFirma.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'isbirligi-firma/:id',
        component: IsbirligiFirmaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.isbirligiFirma.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const isbirligiFirmaPopupRoute: Routes = [
    {
        path: 'isbirligi-firma-new',
        component: IsbirligiFirmaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.isbirligiFirma.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'isbirligi-firma/:id/edit',
        component: IsbirligiFirmaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.isbirligiFirma.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'isbirligi-firma/:id/delete',
        component: IsbirligiFirmaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.isbirligiFirma.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
