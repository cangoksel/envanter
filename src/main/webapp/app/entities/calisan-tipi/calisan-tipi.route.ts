import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CalisanTipiComponent } from './calisan-tipi.component';
import { CalisanTipiDetailComponent } from './calisan-tipi-detail.component';
import { CalisanTipiPopupComponent } from './calisan-tipi-dialog.component';
import { CalisanTipiDeletePopupComponent } from './calisan-tipi-delete-dialog.component';

export const calisanTipiRoute: Routes = [
    {
        path: 'calisan-tipi',
        component: CalisanTipiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.calisanTipi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'calisan-tipi/:id',
        component: CalisanTipiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.calisanTipi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const calisanTipiPopupRoute: Routes = [
    {
        path: 'calisan-tipi-new',
        component: CalisanTipiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.calisanTipi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'calisan-tipi/:id/edit',
        component: CalisanTipiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.calisanTipi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'calisan-tipi/:id/delete',
        component: CalisanTipiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.calisanTipi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
