import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UrunAltKoduComponent } from './urun-alt-kodu.component';
import { UrunAltKoduDetailComponent } from './urun-alt-kodu-detail.component';
import { UrunAltKoduPopupComponent } from './urun-alt-kodu-dialog.component';
import { UrunAltKoduDeletePopupComponent } from './urun-alt-kodu-delete-dialog.component';

export const urunAltKoduRoute: Routes = [
    {
        path: 'urun-alt-kodu',
        component: UrunAltKoduComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunAltKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'urun-alt-kodu/:id',
        component: UrunAltKoduDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunAltKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const urunAltKoduPopupRoute: Routes = [
    {
        path: 'urun-alt-kodu-new',
        component: UrunAltKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunAltKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'urun-alt-kodu/:id/edit',
        component: UrunAltKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunAltKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'urun-alt-kodu/:id/delete',
        component: UrunAltKoduDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunAltKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
