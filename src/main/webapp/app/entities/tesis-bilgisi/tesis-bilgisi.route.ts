import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TesisBilgisiComponent } from './tesis-bilgisi.component';
import { TesisBilgisiDetailComponent } from './tesis-bilgisi-detail.component';
import { TesisBilgisiPopupComponent } from './tesis-bilgisi-dialog.component';
import { TesisBilgisiDeletePopupComponent } from './tesis-bilgisi-delete-dialog.component';

export const tesisBilgisiRoute: Routes = [
    {
        path: 'tesis-bilgisi',
        component: TesisBilgisiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.tesisBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tesis-bilgisi/:id',
        component: TesisBilgisiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.tesisBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tesisBilgisiPopupRoute: Routes = [
    {
        path: 'tesis-bilgisi-new',
        component: TesisBilgisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.tesisBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tesis-bilgisi/:id/edit',
        component: TesisBilgisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.tesisBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tesis-bilgisi/:id/delete',
        component: TesisBilgisiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.tesisBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
