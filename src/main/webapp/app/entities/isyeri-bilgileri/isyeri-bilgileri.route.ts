import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { IsyeriBilgileriComponent } from './isyeri-bilgileri.component';
import { IsyeriBilgileriDetailComponent } from './isyeri-bilgileri-detail.component';
import { IsyeriBilgileriPopupComponent } from './isyeri-bilgileri-dialog.component';
import { IsyeriBilgileriDeletePopupComponent } from './isyeri-bilgileri-delete-dialog.component';

export const isyeriBilgileriRoute: Routes = [
    {
        path: 'isyeri-bilgileri',
        component: IsyeriBilgileriComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.isyeriBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'isyeri-bilgileri/:id',
        component: IsyeriBilgileriDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.isyeriBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const isyeriBilgileriPopupRoute: Routes = [
    {
        path: 'isyeri-bilgileri-new',
        component: IsyeriBilgileriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.isyeriBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'isyeri-bilgileri/:id/edit',
        component: IsyeriBilgileriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.isyeriBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'isyeri-bilgileri/:id/delete',
        component: IsyeriBilgileriDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.isyeriBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
