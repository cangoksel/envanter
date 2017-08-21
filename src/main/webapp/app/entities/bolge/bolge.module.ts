import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    BolgeService,
    BolgePopupService,
    BolgeComponent,
    BolgeDetailComponent,
    BolgeDialogComponent,
    BolgePopupComponent,
    BolgeDeletePopupComponent,
    BolgeDeleteDialogComponent,
    bolgeRoute,
    bolgePopupRoute,
} from './';

const ENTITY_STATES = [
    ...bolgeRoute,
    ...bolgePopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BolgeComponent,
        BolgeDetailComponent,
        BolgeDialogComponent,
        BolgeDeleteDialogComponent,
        BolgePopupComponent,
        BolgeDeletePopupComponent,
    ],
    entryComponents: [
        BolgeComponent,
        BolgeDialogComponent,
        BolgePopupComponent,
        BolgeDeleteDialogComponent,
        BolgeDeletePopupComponent,
    ],
    providers: [
        BolgeService,
        BolgePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryBolgeModule {}
