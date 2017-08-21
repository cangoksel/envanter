import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { KurumComponent } from './kurum.component';
import { KurumDetailComponent } from './kurum-detail.component';
import { KurumPopupComponent } from './kurum-dialog.component';
import { KurumDeletePopupComponent } from './kurum-delete-dialog.component';

export const kurumRoute: Routes = [
    {
        path: 'kurum',
        component: KurumComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurum.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'kurum/:id',
        component: KurumDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurum.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kurumPopupRoute: Routes = [
    {
        path: 'kurum-new',
        component: KurumPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kurum/:id/edit',
        component: KurumPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kurum/:id/delete',
        component: KurumDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kurum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
