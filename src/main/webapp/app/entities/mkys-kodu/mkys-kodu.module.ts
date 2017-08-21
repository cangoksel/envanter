import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    MkysKoduService,
    MkysKoduPopupService,
    MkysKoduComponent,
    MkysKoduDetailComponent,
    MkysKoduDialogComponent,
    MkysKoduPopupComponent,
    MkysKoduDeletePopupComponent,
    MkysKoduDeleteDialogComponent,
    mkysKoduRoute,
    mkysKoduPopupRoute,
} from './';

const ENTITY_STATES = [
    ...mkysKoduRoute,
    ...mkysKoduPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MkysKoduComponent,
        MkysKoduDetailComponent,
        MkysKoduDialogComponent,
        MkysKoduDeleteDialogComponent,
        MkysKoduPopupComponent,
        MkysKoduDeletePopupComponent,
    ],
    entryComponents: [
        MkysKoduComponent,
        MkysKoduDialogComponent,
        MkysKoduPopupComponent,
        MkysKoduDeleteDialogComponent,
        MkysKoduDeletePopupComponent,
    ],
    providers: [
        MkysKoduService,
        MkysKoduPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryMkysKoduModule {}
