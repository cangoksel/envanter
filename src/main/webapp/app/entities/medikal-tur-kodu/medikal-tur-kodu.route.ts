import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MedikalTurKoduComponent } from './medikal-tur-kodu.component';
import { MedikalTurKoduDetailComponent } from './medikal-tur-kodu-detail.component';
import { MedikalTurKoduPopupComponent } from './medikal-tur-kodu-dialog.component';
import { MedikalTurKoduDeletePopupComponent } from './medikal-tur-kodu-delete-dialog.component';

export const medikalTurKoduRoute: Routes = [
    {
        path: 'medikal-tur-kodu',
        component: MedikalTurKoduComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.medikalTurKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'medikal-tur-kodu/:id',
        component: MedikalTurKoduDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.medikalTurKodu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const medikalTurKoduPopupRoute: Routes = [
    {
        path: 'medikal-tur-kodu-new',
        component: MedikalTurKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.medikalTurKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'medikal-tur-kodu/:id/edit',
        component: MedikalTurKoduPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.medikalTurKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'medikal-tur-kodu/:id/delete',
        component: MedikalTurKoduDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'inventoryApp.medikalTurKodu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
