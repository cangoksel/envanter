import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    SayisalVeriService,
    SayisalVeriPopupService,
    SayisalVeriComponent,
    SayisalVeriDetailComponent,
    SayisalVeriDialogComponent,
    SayisalVeriPopupComponent,
    SayisalVeriDeletePopupComponent,
    SayisalVeriDeleteDialogComponent,
    sayisalVeriRoute,
    sayisalVeriPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sayisalVeriRoute,
    ...sayisalVeriPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SayisalVeriComponent,
        SayisalVeriDetailComponent,
        SayisalVeriDialogComponent,
        SayisalVeriDeleteDialogComponent,
        SayisalVeriPopupComponent,
        SayisalVeriDeletePopupComponent,
    ],
    entryComponents: [
        SayisalVeriComponent,
        SayisalVeriDialogComponent,
        SayisalVeriPopupComponent,
        SayisalVeriDeleteDialogComponent,
        SayisalVeriDeletePopupComponent,
    ],
    providers: [
        SayisalVeriService,
        SayisalVeriPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventorySayisalVeriModule {}
