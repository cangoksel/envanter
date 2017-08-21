import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { IhracatComponent } from './ihracat.component';
import { IhracatDetailComponent } from './ihracat-detail.component';
import { IhracatPopupComponent } from './ihracat-dialog.component';
import { IhracatDeletePopupComponent } from './ihracat-delete-dialog.component';

export const ihracatRoute: Routes = [
    {
        path: 'ihracat',
        component: IhracatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ihracat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ihracat/:id',
        component: IhracatDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ihracat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ihracatPopupRoute: Routes = [
    {
        path: 'ihracat-new',
        component: IhracatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ihracat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ihracat/:id/edit',
        component: IhracatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ihracat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ihracat/:id/delete',
        component: IhracatDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.ihracat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
