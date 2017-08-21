import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    NaceKoduService,
    NaceKoduPopupService,
    NaceKoduComponent,
    NaceKoduDetailComponent,
    NaceKoduDialogComponent,
    NaceKoduPopupComponent,
    NaceKoduDeletePopupComponent,
    NaceKoduDeleteDialogComponent,
    naceKoduRoute,
    naceKoduPopupRoute,
} from './';

const ENTITY_STATES = [
    ...naceKoduRoute,
    ...naceKoduPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        NaceKoduComponent,
        NaceKoduDetailComponent,
        NaceKoduDialogComponent,
        NaceKoduDeleteDialogComponent,
        NaceKoduPopupComponent,
        NaceKoduDeletePopupComponent,
    ],
    entryComponents: [
        NaceKoduComponent,
        NaceKoduDialogComponent,
        NaceKoduPopupComponent,
        NaceKoduDeleteDialogComponent,
        NaceKoduDeletePopupComponent,
    ],
    providers: [
        NaceKoduService,
        NaceKoduPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryNaceKoduModule {}
