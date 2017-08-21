import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FinansalBilgileriComponent } from './finansal-bilgileri.component';
import { FinansalBilgileriDetailComponent } from './finansal-bilgileri-detail.component';
import { FinansalBilgileriPopupComponent } from './finansal-bilgileri-dialog.component';
import { FinansalBilgileriDeletePopupComponent } from './finansal-bilgileri-delete-dialog.component';

export const finansalBilgileriRoute: Routes = [
    {
        path: 'finansal-bilgileri',
        component: FinansalBilgileriComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.finansalBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'finansal-bilgileri/:id',
        component: FinansalBilgileriDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.finansalBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const finansalBilgileriPopupRoute: Routes = [
    {
        path: 'finansal-bilgileri-new',
        component: FinansalBilgileriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.finansalBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'finansal-bilgileri/:id/edit',
        component: FinansalBilgileriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.finansalBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'finansal-bilgileri/:id/delete',
        component: FinansalBilgileriDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.finansalBilgileri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
