import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { GtipKoduComponent } from './gtip-kodu.component';
import { GtipKoduDetailComponent } from './gtip-kodu-detail.component';
import { GtipKoduPopupComponent } from './gtip-kodu-dialog.component';
import { GtipKoduDeletePopupComponent } from './gtip-kodu-delete-dialog.component';

export const gtipKoduRoute: Routes = [
    {
        path: 'gtip-kodu',
        component: GtipKoduComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.gtipKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'gtip-kodu/:id',
        component: GtipKoduDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.gtipKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gtipKoduPopupRoute: Routes = [
    {
        path: 'gtip-kodu-new',
        component: GtipKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.gtipKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gtip-kodu/:id/edit',
        component: GtipKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.gtipKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gtip-kodu/:id/delete',
        component: GtipKoduDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.gtipKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
