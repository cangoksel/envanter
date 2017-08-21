import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { KurulusTipiComponent } from './kurulus-tipi.component';
import { KurulusTipiDetailComponent } from './kurulus-tipi-detail.component';
import { KurulusTipiPopupComponent } from './kurulus-tipi-dialog.component';
import { KurulusTipiDeletePopupComponent } from './kurulus-tipi-delete-dialog.component';

export const kurulusTipiRoute: Routes = [
    {
        path: 'kurulus-tipi',
        component: KurulusTipiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurulusTipi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'kurulus-tipi/:id',
        component: KurulusTipiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurulusTipi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kurulusTipiPopupRoute: Routes = [
    {
        path: 'kurulus-tipi-new',
        component: KurulusTipiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurulusTipi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kurulus-tipi/:id/edit',
        component: KurulusTipiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurulusTipi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kurulus-tipi/:id/delete',
        component: KurulusTipiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurulusTipi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
