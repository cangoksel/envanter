import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UretimBilgisiComponent } from './uretim-bilgisi.component';
import { UretimBilgisiDetailComponent } from './uretim-bilgisi-detail.component';
import { UretimBilgisiPopupComponent } from './uretim-bilgisi-dialog.component';
import { UretimBilgisiDeletePopupComponent } from './uretim-bilgisi-delete-dialog.component';

export const uretimBilgisiRoute: Routes = [
    {
        path: 'uretim-bilgisi',
        component: UretimBilgisiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.uretimBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'uretim-bilgisi/:id',
        component: UretimBilgisiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.uretimBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const uretimBilgisiPopupRoute: Routes = [
    {
        path: 'uretim-bilgisi-new',
        component: UretimBilgisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.uretimBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'uretim-bilgisi/:id/edit',
        component: UretimBilgisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.uretimBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'uretim-bilgisi/:id/delete',
        component: UretimBilgisiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.uretimBilgisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
