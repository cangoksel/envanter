import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    GenelFirmaBilgileriService,
    GenelFirmaBilgileriPopupService,
    GenelFirmaBilgileriComponent,
    GenelFirmaBilgileriDetailComponent,
    GenelFirmaBilgileriDialogComponent,
    GenelFirmaBilgileriPopupComponent,
    GenelFirmaBilgileriDeletePopupComponent,
    GenelFirmaBilgileriDeleteDialogComponent,
    genelFirmaBilgileriRoute,
    genelFirmaBilgileriPopupRoute,
} from './';

const ENTITY_STATES = [
    ...genelFirmaBilgileriRoute,
    ...genelFirmaBilgileriPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GenelFirmaBilgileriComponent,
        GenelFirmaBilgileriDetailComponent,
        GenelFirmaBilgileriDialogComponent,
        GenelFirmaBilgileriDeleteDialogComponent,
        GenelFirmaBilgileriPopupComponent,
        GenelFirmaBilgileriDeletePopupComponent,
    ],
    entryComponents: [
        GenelFirmaBilgileriComponent,
        GenelFirmaBilgileriDialogComponent,
        GenelFirmaBilgileriPopupComponent,
        GenelFirmaBilgileriDeleteDialogComponent,
        GenelFirmaBilgileriDeletePopupComponent,
    ],
    providers: [
        GenelFirmaBilgileriService,
        GenelFirmaBilgileriPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryGenelFirmaBilgileriModule {}
