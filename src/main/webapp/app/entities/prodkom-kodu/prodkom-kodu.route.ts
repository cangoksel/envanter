import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProdkomKoduComponent } from './prodkom-kodu.component';
import { ProdkomKoduDetailComponent } from './prodkom-kodu-detail.component';
import { ProdkomKoduPopupComponent } from './prodkom-kodu-dialog.component';
import { ProdkomKoduDeletePopupComponent } from './prodkom-kodu-delete-dialog.component';

export const prodkomKoduRoute: Routes = [
    {
        path: 'prodkom-kodu',
        component: ProdkomKoduComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.prodkomKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prodkom-kodu/:id',
        component: ProdkomKoduDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.prodkomKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prodkomKoduPopupRoute: Routes = [
    {
        path: 'prodkom-kodu-new',
        component: ProdkomKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.prodkomKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prodkom-kodu/:id/edit',
        component: ProdkomKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.prodkomKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prodkom-kodu/:id/delete',
        component: ProdkomKoduDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.prodkomKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
