import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UrunComponent } from './urun.component';
import { UrunDetailComponent } from './urun-detail.component';
import { UrunPopupComponent } from './urun-dialog.component';
import { UrunDeletePopupComponent } from './urun-delete-dialog.component';

export const urunRoute: Routes = [
    {
        path: 'urun',
        component: UrunComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urun.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'urun/:id',
        component: UrunDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urun.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const urunPopupRoute: Routes = [
    {
        path: 'urun-new',
        component: UrunPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urun.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'urun/:id/edit',
        component: UrunPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urun.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'urun/:id/delete',
        component: UrunDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urun.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
