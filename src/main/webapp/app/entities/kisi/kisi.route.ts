import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { KisiComponent } from './kisi.component';
import { KisiDetailComponent } from './kisi-detail.component';
import { KisiPopupComponent } from './kisi-dialog.component';
import { KisiDeletePopupComponent } from './kisi-delete-dialog.component';

export const kisiRoute: Routes = [
    {
        path: 'kisi',
        component: KisiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'kisi/:id',
        component: KisiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kisiPopupRoute: Routes = [
    {
        path: 'kisi-new',
        component: KisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kisi/:id/edit',
        component: KisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kisi/:id/delete',
        component: KisiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.kisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
