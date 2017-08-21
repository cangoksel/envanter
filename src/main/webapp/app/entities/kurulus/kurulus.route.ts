import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { KurulusComponent } from './kurulus.component';
import { KurulusDetailComponent } from './kurulus-detail.component';
import { KurulusPopupComponent } from './kurulus-dialog.component';
import { KurulusDeletePopupComponent } from './kurulus-delete-dialog.component';

export const kurulusRoute: Routes = [
    {
        path: 'kurulus',
        component: KurulusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurulus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'kurulus/:id',
        component: KurulusDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurulus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kurulusPopupRoute: Routes = [
    {
        path: 'kurulus-new',
        component: KurulusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurulus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kurulus/:id/edit',
        component: KurulusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurulus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kurulus/:id/delete',
        component: KurulusDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurulus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
