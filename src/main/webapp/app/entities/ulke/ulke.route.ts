import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UlkeComponent } from './ulke.component';
import { UlkeDetailComponent } from './ulke-detail.component';
import { UlkePopupComponent } from './ulke-dialog.component';
import { UlkeDeletePopupComponent } from './ulke-delete-dialog.component';

export const ulkeRoute: Routes = [
    {
        path: 'ulke',
        component: UlkeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ulke.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ulke/:id',
        component: UlkeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ulke.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ulkePopupRoute: Routes = [
    {
        path: 'ulke-new',
        component: UlkePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ulke.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ulke/:id/edit',
        component: UlkePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ulke.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ulke/:id/delete',
        component: UlkeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ulke.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
