import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BolgeComponent } from './bolge.component';
import { BolgeDetailComponent } from './bolge-detail.component';
import { BolgePopupComponent } from './bolge-dialog.component';
import { BolgeDeletePopupComponent } from './bolge-delete-dialog.component';

export const bolgeRoute: Routes = [
    {
        path: 'bolge',
        component: BolgeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.bolge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bolge/:id',
        component: BolgeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.bolge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bolgePopupRoute: Routes = [
    {
        path: 'bolge-new',
        component: BolgePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.bolge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bolge/:id/edit',
        component: BolgePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.bolge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bolge/:id/delete',
        component: BolgeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.bolge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
