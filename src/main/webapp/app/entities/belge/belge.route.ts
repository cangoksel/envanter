import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BelgeComponent } from './belge.component';
import { BelgeDetailComponent } from './belge-detail.component';
import { BelgePopupComponent } from './belge-dialog.component';
import { BelgeDeletePopupComponent } from './belge-delete-dialog.component';

export const belgeRoute: Routes = [
    {
        path: 'belge',
        component: BelgeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.belge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'belge/:id',
        component: BelgeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.belge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const belgePopupRoute: Routes = [
    {
        path: 'belge-new',
        component: BelgePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.belge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'belge/:id/edit',
        component: BelgePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.belge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'belge/:id/delete',
        component: BelgeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.belge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
