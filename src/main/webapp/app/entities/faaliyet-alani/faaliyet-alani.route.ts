import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FaaliyetAlaniComponent } from './faaliyet-alani.component';
import { FaaliyetAlaniDetailComponent } from './faaliyet-alani-detail.component';
import { FaaliyetAlaniPopupComponent } from './faaliyet-alani-dialog.component';
import { FaaliyetAlaniDeletePopupComponent } from './faaliyet-alani-delete-dialog.component';

export const faaliyetAlaniRoute: Routes = [
    {
        path: 'faaliyet-alani',
        component: FaaliyetAlaniComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.faaliyetAlani.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'faaliyet-alani/:id',
        component: FaaliyetAlaniDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.faaliyetAlani.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const faaliyetAlaniPopupRoute: Routes = [
    {
        path: 'faaliyet-alani-new',
        component: FaaliyetAlaniPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.faaliyetAlani.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'faaliyet-alani/:id/edit',
        component: FaaliyetAlaniPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.faaliyetAlani.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'faaliyet-alani/:id/delete',
        component: FaaliyetAlaniDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.faaliyetAlani.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
