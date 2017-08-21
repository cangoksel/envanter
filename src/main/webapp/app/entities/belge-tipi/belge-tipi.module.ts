import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    BelgeTipiService,
    BelgeTipiPopupService,
    BelgeTipiComponent,
    BelgeTipiDetailComponent,
    BelgeTipiDialogComponent,
    BelgeTipiPopupComponent,
    BelgeTipiDeletePopupComponent,
    BelgeTipiDeleteDialogComponent,
    belgeTipiRoute,
    belgeTipiPopupRoute,
} from './';

const ENTITY_STATES = [
    ...belgeTipiRoute,
    ...belgeTipiPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BelgeTipiComponent,
        BelgeTipiDetailComponent,
        BelgeTipiDialogComponent,
        BelgeTipiDeleteDialogComponent,
        BelgeTipiPopupComponent,
        BelgeTipiDeletePopupComponent,
    ],
    entryComponents: [
        BelgeTipiComponent,
        BelgeTipiDialogComponent,
        BelgeTipiPopupComponent,
        BelgeTipiDeleteDialogComponent,
        BelgeTipiDeletePopupComponent,
    ],
    providers: [
        BelgeTipiService,
        BelgeTipiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryBelgeTipiModule {}
