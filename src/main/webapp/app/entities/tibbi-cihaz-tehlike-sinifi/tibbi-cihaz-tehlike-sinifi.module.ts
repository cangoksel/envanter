import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    TibbiCihazTehlikeSinifiService,
    TibbiCihazTehlikeSinifiPopupService,
    TibbiCihazTehlikeSinifiComponent,
    TibbiCihazTehlikeSinifiDetailComponent,
    TibbiCihazTehlikeSinifiDialogComponent,
    TibbiCihazTehlikeSinifiPopupComponent,
    TibbiCihazTehlikeSinifiDeletePopupComponent,
    TibbiCihazTehlikeSinifiDeleteDialogComponent,
    tibbiCihazTehlikeSinifiRoute,
    tibbiCihazTehlikeSinifiPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tibbiCihazTehlikeSinifiRoute,
    ...tibbiCihazTehlikeSinifiPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TibbiCihazTehlikeSinifiComponent,
        TibbiCihazTehlikeSinifiDetailComponent,
        TibbiCihazTehlikeSinifiDialogComponent,
        TibbiCihazTehlikeSinifiDeleteDialogComponent,
        TibbiCihazTehlikeSinifiPopupComponent,
        TibbiCihazTehlikeSinifiDeletePopupComponent,
    ],
    entryComponents: [
        TibbiCihazTehlikeSinifiComponent,
        TibbiCihazTehlikeSinifiDialogComponent,
        TibbiCihazTehlikeSinifiPopupComponent,
        TibbiCihazTehlikeSinifiDeleteDialogComponent,
        TibbiCihazTehlikeSinifiDeletePopupComponent,
    ],
    providers: [
        TibbiCihazTehlikeSinifiService,
        TibbiCihazTehlikeSinifiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryTibbiCihazTehlikeSinifiModule {}
