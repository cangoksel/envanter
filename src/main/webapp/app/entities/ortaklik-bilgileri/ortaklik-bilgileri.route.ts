import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OrtaklikBilgileriComponent } from './ortaklik-bilgileri.component';
import { OrtaklikBilgileriDetailComponent } from './ortaklik-bilgileri-detail.component';
import { OrtaklikBilgileriPopupComponent } from './ortaklik-bilgileri-dialog.component';
import { OrtaklikBilgileriDeletePopupComponent } from './ortaklik-bilgileri-delete-dialog.component';

export const ortaklikBilgileriRoute: Routes = [
    {
        path: 'ortaklik-bilgileri',
        component: OrtaklikBilgileriComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ortaklikBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ortaklik-bilgileri/:id',
        component: OrtaklikBilgileriDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ortaklikBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ortaklikBilgileriPopupRoute: Routes = [
    {
        path: 'ortaklik-bilgileri-new',
        component: OrtaklikBilgileriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ortaklikBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ortaklik-bilgileri/:id/edit',
        component: OrtaklikBilgileriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ortaklikBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ortaklik-bilgileri/:id/delete',
        component: OrtaklikBilgileriDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ortaklikBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
