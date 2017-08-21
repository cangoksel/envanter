import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    UlkeService,
    UlkePopupService,
    UlkeComponent,
    UlkeDetailComponent,
    UlkeDialogComponent,
    UlkePopupComponent,
    UlkeDeletePopupComponent,
    UlkeDeleteDialogComponent,
    ulkeRoute,
    ulkePopupRoute,
} from './';

const ENTITY_STATES = [
    ...ulkeRoute,
    ...ulkePopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UlkeComponent,
        UlkeDetailComponent,
        UlkeDialogComponent,
        UlkeDeleteDialogComponent,
        UlkePopupComponent,
        UlkeDeletePopupComponent,
    ],
    entryComponents: [
        UlkeComponent,
        UlkeDialogComponent,
        UlkePopupComponent,
        UlkeDeleteDialogComponent,
        UlkeDeletePopupComponent,
    ],
    providers: [
        UlkeService,
        UlkePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryUlkeModule {}
