import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { GenelFirmaBilgileriComponent } from './genel-firma-bilgileri.component';
import { GenelFirmaBilgileriDetailComponent } from './genel-firma-bilgileri-detail.component';
import { GenelFirmaBilgileriPopupComponent } from './genel-firma-bilgileri-dialog.component';
import { GenelFirmaBilgileriDeletePopupComponent } from './genel-firma-bilgileri-delete-dialog.component';

export const genelFirmaBilgileriRoute: Routes = [
    {
        path: 'genel-firma-bilgileri',
        component: GenelFirmaBilgileriComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.genelFirmaBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'genel-firma-bilgileri/:id',
        component: GenelFirmaBilgileriDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.genelFirmaBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const genelFirmaBilgileriPopupRoute: Routes = [
    {
        path: 'genel-firma-bilgileri-new',
        component: GenelFirmaBilgileriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.genelFirmaBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'genel-firma-bilgileri/:id/edit',
        component: GenelFirmaBilgileriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.genelFirmaBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'genel-firma-bilgileri/:id/delete',
        component: GenelFirmaBilgileriDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.genelFirmaBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
