import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SayisalVeriComponent } from './sayisal-veri.component';
import { SayisalVeriDetailComponent } from './sayisal-veri-detail.component';
import { SayisalVeriPopupComponent } from './sayisal-veri-dialog.component';
import { SayisalVeriDeletePopupComponent } from './sayisal-veri-delete-dialog.component';

export const sayisalVeriRoute: Routes = [
    {
        path: 'sayisal-veri',
        component: SayisalVeriComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.sayisalVeri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sayisal-veri/:id',
        component: SayisalVeriDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.sayisalVeri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sayisalVeriPopupRoute: Routes = [
    {
        path: 'sayisal-veri-new',
        component: SayisalVeriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.sayisalVeri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sayisal-veri/:id/edit',
        component: SayisalVeriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.sayisalVeri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sayisal-veri/:id/delete',
        component: SayisalVeriDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.sayisalVeri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
