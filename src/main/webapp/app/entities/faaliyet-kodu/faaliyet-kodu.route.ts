import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FaaliyetKoduComponent } from './faaliyet-kodu.component';
import { FaaliyetKoduDetailComponent } from './faaliyet-kodu-detail.component';
import { FaaliyetKoduPopupComponent } from './faaliyet-kodu-dialog.component';
import { FaaliyetKoduDeletePopupComponent } from './faaliyet-kodu-delete-dialog.component';

export const faaliyetKoduRoute: Routes = [
    {
        path: 'faaliyet-kodu',
        component: FaaliyetKoduComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.faaliyetKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'faaliyet-kodu/:id',
        component: FaaliyetKoduDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.faaliyetKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const faaliyetKoduPopupRoute: Routes = [
    {
        path: 'faaliyet-kodu-new',
        component: FaaliyetKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.faaliyetKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'faaliyet-kodu/:id/edit',
        component: FaaliyetKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.faaliyetKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'faaliyet-kodu/:id/delete',
        component: FaaliyetKoduDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.faaliyetKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
