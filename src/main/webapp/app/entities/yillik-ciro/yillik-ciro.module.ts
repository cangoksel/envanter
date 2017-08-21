import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    YillikCiroService,
    YillikCiroPopupService,
    YillikCiroComponent,
    YillikCiroDetailComponent,
    YillikCiroDialogComponent,
    YillikCiroPopupComponent,
    YillikCiroDeletePopupComponent,
    YillikCiroDeleteDialogComponent,
    yillikCiroRoute,
    yillikCiroPopupRoute,
} from './';

const ENTITY_STATES = [
    ...yillikCiroRoute,
    ...yillikCiroPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        YillikCiroComponent,
        YillikCiroDetailComponent,
        YillikCiroDialogComponent,
        YillikCiroDeleteDialogComponent,
        YillikCiroPopupComponent,
        YillikCiroDeletePopupComponent,
    ],
    entryComponents: [
        YillikCiroComponent,
        YillikCiroDialogComponent,
        YillikCiroPopupComponent,
        YillikCiroDeleteDialogComponent,
        YillikCiroDeletePopupComponent,
    ],
    providers: [
        YillikCiroService,
        YillikCiroPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryYillikCiroModule {}
