import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    CalisanTipiService,
    CalisanTipiPopupService,
    CalisanTipiComponent,
    CalisanTipiDetailComponent,
    CalisanTipiDialogComponent,
    CalisanTipiPopupComponent,
    CalisanTipiDeletePopupComponent,
    CalisanTipiDeleteDialogComponent,
    calisanTipiRoute,
    calisanTipiPopupRoute,
} from './';

const ENTITY_STATES = [
    ...calisanTipiRoute,
    ...calisanTipiPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CalisanTipiComponent,
        CalisanTipiDetailComponent,
        CalisanTipiDialogComponent,
        CalisanTipiDeleteDialogComponent,
        CalisanTipiPopupComponent,
        CalisanTipiDeletePopupComponent,
    ],
    entryComponents: [
        CalisanTipiComponent,
        CalisanTipiDialogComponent,
        CalisanTipiPopupComponent,
        CalisanTipiDeleteDialogComponent,
        CalisanTipiDeletePopupComponent,
    ],
    providers: [
        CalisanTipiService,
        CalisanTipiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryCalisanTipiModule {}
