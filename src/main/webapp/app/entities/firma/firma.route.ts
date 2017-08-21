import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FirmaComponent } from './firma.component';
import { FirmaDetailComponent } from './firma-detail.component';
import { FirmaPopupComponent } from './firma-dialog.component';
import { FirmaDeletePopupComponent } from './firma-delete-dialog.component';

export const firmaRoute: Routes = [
    {
        path: 'firma',
        component: FirmaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.firma.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'firma/:id',
        component: FirmaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.firma.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const firmaPopupRoute: Routes = [
    {
        path: 'firma-new',
        component: FirmaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.firma.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'firma/:id/edit',
        component: FirmaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.firma.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'firma/:id/delete',
        component: FirmaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.firma.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
