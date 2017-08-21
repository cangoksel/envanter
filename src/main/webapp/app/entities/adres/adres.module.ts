import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    AdresService,
    AdresPopupService,
    AdresComponent,
    AdresDetailComponent,
    AdresDialogComponent,
    AdresPopupComponent,
    AdresDeletePopupComponent,
    AdresDeleteDialogComponent,
    adresRoute,
    adresPopupRoute,
} from './';

const ENTITY_STATES = [
    ...adresRoute,
    ...adresPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AdresComponent,
        AdresDetailComponent,
        AdresDialogComponent,
        AdresDeleteDialogComponent,
        AdresPopupComponent,
        AdresDeletePopupComponent,
    ],
    entryComponents: [
        AdresComponent,
        AdresDialogComponent,
        AdresPopupComponent,
        AdresDeleteDialogComponent,
        AdresDeletePopupComponent,
    ],
    providers: [
        AdresService,
        AdresPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryAdresModule {}
