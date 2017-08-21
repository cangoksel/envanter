import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    KisiService,
    KisiPopupService,
    KisiComponent,
    KisiDetailComponent,
    KisiDialogComponent,
    KisiPopupComponent,
    KisiDeletePopupComponent,
    KisiDeleteDialogComponent,
    kisiRoute,
    kisiPopupRoute,
} from './';

const ENTITY_STATES = [
    ...kisiRoute,
    ...kisiPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KisiComponent,
        KisiDetailComponent,
        KisiDialogComponent,
        KisiDeleteDialogComponent,
        KisiPopupComponent,
        KisiDeletePopupComponent,
    ],
    entryComponents: [
        KisiComponent,
        KisiDialogComponent,
        KisiPopupComponent,
        KisiDeleteDialogComponent,
        KisiDeletePopupComponent,
    ],
    providers: [
        KisiService,
        KisiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryKisiModule {}
