import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UrunKoduComponent } from './urun-kodu.component';
import { UrunKoduDetailComponent } from './urun-kodu-detail.component';
import { UrunKoduPopupComponent } from './urun-kodu-dialog.component';
import { UrunKoduDeletePopupComponent } from './urun-kodu-delete-dialog.component';

export const urunKoduRoute: Routes = [
    {
        path: 'urun-kodu',
        component: UrunKoduComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'urun-kodu/:id',
        component: UrunKoduDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const urunKoduPopupRoute: Routes = [
    {
        path: 'urun-kodu-new',
        component: UrunKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'urun-kodu/:id/edit',
        component: UrunKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'urun-kodu/:id/delete',
        component: UrunKoduDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
