import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { NaceKoduComponent } from './nace-kodu.component';
import { NaceKoduDetailComponent } from './nace-kodu-detail.component';
import { NaceKoduPopupComponent } from './nace-kodu-dialog.component';
import { NaceKoduDeletePopupComponent } from './nace-kodu-delete-dialog.component';

export const naceKoduRoute: Routes = [
    {
        path: 'nace-kodu',
        component: NaceKoduComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.naceKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'nace-kodu/:id',
        component: NaceKoduDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.naceKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const naceKoduPopupRoute: Routes = [
    {
        path: 'nace-kodu-new',
        component: NaceKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.naceKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nace-kodu/:id/edit',
        component: NaceKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.naceKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nace-kodu/:id/delete',
        component: NaceKoduDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.naceKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
