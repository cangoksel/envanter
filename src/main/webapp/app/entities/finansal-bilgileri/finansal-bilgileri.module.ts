import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    FinansalBilgileriService,
    FinansalBilgileriPopupService,
    FinansalBilgileriComponent,
    FinansalBilgileriDetailComponent,
    FinansalBilgileriDialogComponent,
    FinansalBilgileriPopupComponent,
    FinansalBilgileriDeletePopupComponent,
    FinansalBilgileriDeleteDialogComponent,
    finansalBilgileriRoute,
    finansalBilgileriPopupRoute,
} from './';

const ENTITY_STATES = [
    ...finansalBilgileriRoute,
    ...finansalBilgileriPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FinansalBilgileriComponent,
        FinansalBilgileriDetailComponent,
        FinansalBilgileriDialogComponent,
        FinansalBilgileriDeleteDialogComponent,
        FinansalBilgileriPopupComponent,
        FinansalBilgileriDeletePopupComponent,
    ],
    entryComponents: [
        FinansalBilgileriComponent,
        FinansalBilgileriDialogComponent,
        FinansalBilgileriPopupComponent,
        FinansalBilgileriDeleteDialogComponent,
        FinansalBilgileriDeletePopupComponent,
    ],
    providers: [
        FinansalBilgileriService,
        FinansalBilgileriPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryFinansalBilgileriModule {}
