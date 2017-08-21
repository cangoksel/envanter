import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MkysKoduComponent } from './mkys-kodu.component';
import { MkysKoduDetailComponent } from './mkys-kodu-detail.component';
import { MkysKoduPopupComponent } from './mkys-kodu-dialog.component';
import { MkysKoduDeletePopupComponent } from './mkys-kodu-delete-dialog.component';

export const mkysKoduRoute: Routes = [
    {
        path: 'mkys-kodu',
        component: MkysKoduComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.mkysKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mkys-kodu/:id',
        component: MkysKoduDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.mkysKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mkysKoduPopupRoute: Routes = [
    {
        path: 'mkys-kodu-new',
        component: MkysKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.mkysKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mkys-kodu/:id/edit',
        component: MkysKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.mkysKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mkys-kodu/:id/delete',
        component: MkysKoduDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.mkysKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
