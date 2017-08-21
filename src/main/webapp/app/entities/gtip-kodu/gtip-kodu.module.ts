import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    GtipKoduService,
    GtipKoduPopupService,
    GtipKoduComponent,
    GtipKoduDetailComponent,
    GtipKoduDialogComponent,
    GtipKoduPopupComponent,
    GtipKoduDeletePopupComponent,
    GtipKoduDeleteDialogComponent,
    gtipKoduRoute,
    gtipKoduPopupRoute,
} from './';

const ENTITY_STATES = [
    ...gtipKoduRoute,
    ...gtipKoduPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GtipKoduComponent,
        GtipKoduDetailComponent,
        GtipKoduDialogComponent,
        GtipKoduDeleteDialogComponent,
        GtipKoduPopupComponent,
        GtipKoduDeletePopupComponent,
    ],
    entryComponents: [
        GtipKoduComponent,
        GtipKoduDialogComponent,
        GtipKoduPopupComponent,
        GtipKoduDeleteDialogComponent,
        GtipKoduDeletePopupComponent,
    ],
    providers: [
        GtipKoduService,
        GtipKoduPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryGtipKoduModule {}
