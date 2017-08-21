import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { IlComponent } from './il.component';
import { IlDetailComponent } from './il-detail.component';
import { IlPopupComponent } from './il-dialog.component';
import { IlDeletePopupComponent } from './il-delete-dialog.component';

export const ilRoute: Routes = [
    {
        path: 'il',
        component: IlComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.il.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'il/:id',
        component: IlDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.il.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ilPopupRoute: Routes = [
    {
        path: 'il-new',
        component: IlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.il.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'il/:id/edit',
        component: IlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.il.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'il/:id/delete',
        component: IlDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.il.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
