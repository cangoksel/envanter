import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    IhracatService,
    IhracatPopupService,
    IhracatComponent,
    IhracatDetailComponent,
    IhracatDialogComponent,
    IhracatPopupComponent,
    IhracatDeletePopupComponent,
    IhracatDeleteDialogComponent,
    ihracatRoute,
    ihracatPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ihracatRoute,
    ...ihracatPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        IhracatComponent,
        IhracatDetailComponent,
        IhracatDialogComponent,
        IhracatDeleteDialogComponent,
        IhracatPopupComponent,
        IhracatDeletePopupComponent,
    ],
    entryComponents: [
        IhracatComponent,
        IhracatDialogComponent,
        IhracatPopupComponent,
        IhracatDeleteDialogComponent,
        IhracatDeletePopupComponent,
    ],
    providers: [
        IhracatService,
        IhracatPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryIhracatModule {}
