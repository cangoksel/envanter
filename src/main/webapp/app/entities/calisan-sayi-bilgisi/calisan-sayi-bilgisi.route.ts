import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CalisanSayiBilgisiComponent } from './calisan-sayi-bilgisi.component';
import { CalisanSayiBilgisiDetailComponent } from './calisan-sayi-bilgisi-detail.component';
import { CalisanSayiBilgisiPopupComponent } from './calisan-sayi-bilgisi-dialog.component';
import { CalisanSayiBilgisiDeletePopupComponent } from './calisan-sayi-bilgisi-delete-dialog.component';

export const calisanSayiBilgisiRoute: Routes = [
    {
        path: 'calisan-sayi-bilgisi',
        component: CalisanSayiBilgisiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.calisanSayiBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'calisan-sayi-bilgisi/:id',
        component: CalisanSayiBilgisiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.calisanSayiBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const calisanSayiBilgisiPopupRoute: Routes = [
    {
        path: 'calisan-sayi-bilgisi-new',
        component: CalisanSayiBilgisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.calisanSayiBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'calisan-sayi-bilgisi/:id/edit',
        component: CalisanSayiBilgisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.calisanSayiBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'calisan-sayi-bilgisi/:id/delete',
        component: CalisanSayiBilgisiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.calisanSayiBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
