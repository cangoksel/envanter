import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    UrunService,
    UrunPopupService,
    UrunComponent,
    UrunDetailComponent,
    UrunDialogComponent,
    UrunPopupComponent,
    UrunDeletePopupComponent,
    UrunDeleteDialogComponent,
    urunRoute,
    urunPopupRoute,
} from './';

const ENTITY_STATES = [
    ...urunRoute,
    ...urunPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UrunComponent,
        UrunDetailComponent,
        UrunDialogComponent,
        UrunDeleteDialogComponent,
        UrunPopupComponent,
        UrunDeletePopupComponent,
    ],
    entryComponents: [
        UrunComponent,
        UrunDialogComponent,
        UrunPopupComponent,
        UrunDeleteDialogComponent,
        UrunDeletePopupComponent,
    ],
    providers: [
        UrunService,
        UrunPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryUrunModule {}
