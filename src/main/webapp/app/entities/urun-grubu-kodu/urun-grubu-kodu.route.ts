import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UrunGrubuKoduComponent } from './urun-grubu-kodu.component';
import { UrunGrubuKoduDetailComponent } from './urun-grubu-kodu-detail.component';
import { UrunGrubuKoduPopupComponent } from './urun-grubu-kodu-dialog.component';
import { UrunGrubuKoduDeletePopupComponent } from './urun-grubu-kodu-delete-dialog.component';

export const urunGrubuKoduRoute: Routes = [
    {
        path: 'urun-grubu-kodu',
        component: UrunGrubuKoduComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunGrubuKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'urun-grubu-kodu/:id',
        component: UrunGrubuKoduDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunGrubuKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const urunGrubuKoduPopupRoute: Routes = [
    {
        path: 'urun-grubu-kodu-new',
        component: UrunGrubuKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunGrubuKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'urun-grubu-kodu/:id/edit',
        component: UrunGrubuKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunGrubuKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'urun-grubu-kodu/:id/delete',
        component: UrunGrubuKoduDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunGrubuKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
