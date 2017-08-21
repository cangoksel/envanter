import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    IsyeriBilgileriService,
    IsyeriBilgileriPopupService,
    IsyeriBilgileriComponent,
    IsyeriBilgileriDetailComponent,
    IsyeriBilgileriDialogComponent,
    IsyeriBilgileriPopupComponent,
    IsyeriBilgileriDeletePopupComponent,
    IsyeriBilgileriDeleteDialogComponent,
    isyeriBilgileriRoute,
    isyeriBilgileriPopupRoute,
} from './';

const ENTITY_STATES = [
    ...isyeriBilgileriRoute,
    ...isyeriBilgileriPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        IsyeriBilgileriComponent,
        IsyeriBilgileriDetailComponent,
        IsyeriBilgileriDialogComponent,
        IsyeriBilgileriDeleteDialogComponent,
        IsyeriBilgileriPopupComponent,
        IsyeriBilgileriDeletePopupComponent,
    ],
    entryComponents: [
        IsyeriBilgileriComponent,
        IsyeriBilgileriDialogComponent,
        IsyeriBilgileriPopupComponent,
        IsyeriBilgileriDeleteDialogComponent,
        IsyeriBilgileriDeletePopupComponent,
    ],
    providers: [
        IsyeriBilgileriService,
        IsyeriBilgileriPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryIsyeriBilgileriModule {}
