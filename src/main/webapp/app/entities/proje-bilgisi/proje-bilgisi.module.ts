import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    ProjeBilgisiService,
    ProjeBilgisiPopupService,
    ProjeBilgisiComponent,
    ProjeBilgisiDetailComponent,
    ProjeBilgisiDialogComponent,
    ProjeBilgisiPopupComponent,
    ProjeBilgisiDeletePopupComponent,
    ProjeBilgisiDeleteDialogComponent,
    projeBilgisiRoute,
    projeBilgisiPopupRoute,
} from './';

const ENTITY_STATES = [
    ...projeBilgisiRoute,
    ...projeBilgisiPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProjeBilgisiComponent,
        ProjeBilgisiDetailComponent,
        ProjeBilgisiDialogComponent,
        ProjeBilgisiDeleteDialogComponent,
        ProjeBilgisiPopupComponent,
        ProjeBilgisiDeletePopupComponent,
    ],
    entryComponents: [
        ProjeBilgisiComponent,
        ProjeBilgisiDialogComponent,
        ProjeBilgisiPopupComponent,
        ProjeBilgisiDeleteDialogComponent,
        ProjeBilgisiDeletePopupComponent,
    ],
    providers: [
        ProjeBilgisiService,
        ProjeBilgisiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryProjeBilgisiModule {}
