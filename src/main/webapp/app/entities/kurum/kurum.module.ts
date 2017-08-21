import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    KurumService,
    KurumPopupService,
    KurumComponent,
    KurumDetailComponent,
    KurumDialogComponent,
    KurumPopupComponent,
    KurumDeletePopupComponent,
    KurumDeleteDialogComponent,
    kurumRoute,
    kurumPopupRoute,
} from './';

const ENTITY_STATES = [
    ...kurumRoute,
    ...kurumPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KurumComponent,
        KurumDetailComponent,
        KurumDialogComponent,
        KurumDeleteDialogComponent,
        KurumPopupComponent,
        KurumDeletePopupComponent,
    ],
    entryComponents: [
        KurumComponent,
        KurumDialogComponent,
        KurumPopupComponent,
        KurumDeleteDialogComponent,
        KurumDeletePopupComponent,
    ],
    providers: [
        KurumService,
        KurumPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryKurumModule {}
