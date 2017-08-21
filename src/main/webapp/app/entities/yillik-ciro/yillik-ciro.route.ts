import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { YillikCiroComponent } from './yillik-ciro.component';
import { YillikCiroDetailComponent } from './yillik-ciro-detail.component';
import { YillikCiroPopupComponent } from './yillik-ciro-dialog.component';
import { YillikCiroDeletePopupComponent } from './yillik-ciro-delete-dialog.component';

export const yillikCiroRoute: Routes = [
    {
        path: 'yillik-ciro',
        component: YillikCiroComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.yillikCiro.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'yillik-ciro/:id',
        component: YillikCiroDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.yillikCiro.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const yillikCiroPopupRoute: Routes = [
    {
        path: 'yillik-ciro-new',
        component: YillikCiroPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.yillikCiro.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'yillik-ciro/:id/edit',
        component: YillikCiroPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.yillikCiro.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'yillik-ciro/:id/delete',
        component: YillikCiroDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.yillikCiro.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
