import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    OrtaklikBilgileriService,
    OrtaklikBilgileriPopupService,
    OrtaklikBilgileriComponent,
    OrtaklikBilgileriDetailComponent,
    OrtaklikBilgileriDialogComponent,
    OrtaklikBilgileriPopupComponent,
    OrtaklikBilgileriDeletePopupComponent,
    OrtaklikBilgileriDeleteDialogComponent,
    ortaklikBilgileriRoute,
    ortaklikBilgileriPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ortaklikBilgileriRoute,
    ...ortaklikBilgileriPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrtaklikBilgileriComponent,
        OrtaklikBilgileriDetailComponent,
        OrtaklikBilgileriDialogComponent,
        OrtaklikBilgileriDeleteDialogComponent,
        OrtaklikBilgileriPopupComponent,
        OrtaklikBilgileriDeletePopupComponent,
    ],
    entryComponents: [
        OrtaklikBilgileriComponent,
        OrtaklikBilgileriDialogComponent,
        OrtaklikBilgileriPopupComponent,
        OrtaklikBilgileriDeleteDialogComponent,
        OrtaklikBilgileriDeletePopupComponent,
    ],
    providers: [
        OrtaklikBilgileriService,
        OrtaklikBilgileriPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryOrtaklikBilgileriModule {}
