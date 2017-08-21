import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BelgeTipiComponent } from './belge-tipi.component';
import { BelgeTipiDetailComponent } from './belge-tipi-detail.component';
import { BelgeTipiPopupComponent } from './belge-tipi-dialog.component';
import { BelgeTipiDeletePopupComponent } from './belge-tipi-delete-dialog.component';

export const belgeTipiRoute: Routes = [
    {
        path: 'belge-tipi',
        component: BelgeTipiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.belgeTipi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'belge-tipi/:id',
        component: BelgeTipiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.belgeTipi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const belgeTipiPopupRoute: Routes = [
    {
        path: 'belge-tipi-new',
        component: BelgeTipiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.belgeTipi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'belge-tipi/:id/edit',
        component: BelgeTipiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.belgeTipi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'belge-tipi/:id/delete',
        component: BelgeTipiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.belgeTipi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
