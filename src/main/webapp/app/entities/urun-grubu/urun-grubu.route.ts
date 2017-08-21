import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UrunGrubuComponent } from './urun-grubu.component';
import { UrunGrubuDetailComponent } from './urun-grubu-detail.component';
import { UrunGrubuPopupComponent } from './urun-grubu-dialog.component';
import { UrunGrubuDeletePopupComponent } from './urun-grubu-delete-dialog.component';

export const urunGrubuRoute: Routes = [
    {
        path: 'urun-grubu',
        component: UrunGrubuComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunGrubu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'urun-grubu/:id',
        component: UrunGrubuDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunGrubu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const urunGrubuPopupRoute: Routes = [
    {
        path: 'urun-grubu-new',
        component: UrunGrubuPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunGrubu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'urun-grubu/:id/edit',
        component: UrunGrubuPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunGrubu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'urun-grubu/:id/delete',
        component: UrunGrubuDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.urunGrubu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
