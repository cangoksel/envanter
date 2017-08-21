import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TibbiCihazTehlikeSinifiComponent } from './tibbi-cihaz-tehlike-sinifi.component';
import { TibbiCihazTehlikeSinifiDetailComponent } from './tibbi-cihaz-tehlike-sinifi-detail.component';
import { TibbiCihazTehlikeSinifiPopupComponent } from './tibbi-cihaz-tehlike-sinifi-dialog.component';
import { TibbiCihazTehlikeSinifiDeletePopupComponent } from './tibbi-cihaz-tehlike-sinifi-delete-dialog.component';

export const tibbiCihazTehlikeSinifiRoute: Routes = [
    {
        path: 'tibbi-cihaz-tehlike-sinifi',
        component: TibbiCihazTehlikeSinifiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.tibbiCihazTehlikeSinifi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tibbi-cihaz-tehlike-sinifi/:id',
        component: TibbiCihazTehlikeSinifiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.tibbiCihazTehlikeSinifi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tibbiCihazTehlikeSinifiPopupRoute: Routes = [
    {
        path: 'tibbi-cihaz-tehlike-sinifi-new',
        component: TibbiCihazTehlikeSinifiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.tibbiCihazTehlikeSinifi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tibbi-cihaz-tehlike-sinifi/:id/edit',
        component: TibbiCihazTehlikeSinifiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.tibbiCihazTehlikeSinifi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tibbi-cihaz-tehlike-sinifi/:id/delete',
        component: TibbiCihazTehlikeSinifiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.tibbiCihazTehlikeSinifi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
