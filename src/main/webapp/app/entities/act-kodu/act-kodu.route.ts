import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ActKoduComponent } from './act-kodu.component';
import { ActKoduDetailComponent } from './act-kodu-detail.component';
import { ActKoduPopupComponent } from './act-kodu-dialog.component';
import { ActKoduDeletePopupComponent } from './act-kodu-delete-dialog.component';

export const actKoduRoute: Routes = [
    {
        path: 'act-kodu',
        component: ActKoduComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.actKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'act-kodu/:id',
        component: ActKoduDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.actKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actKoduPopupRoute: Routes = [
    {
        path: 'act-kodu-new',
        component: ActKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.actKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'act-kodu/:id/edit',
        component: ActKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.actKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'act-kodu/:id/delete',
        component: ActKoduDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.actKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
