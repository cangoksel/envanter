import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    FaaliyetAlaniService,
    FaaliyetAlaniPopupService,
    FaaliyetAlaniComponent,
    FaaliyetAlaniDetailComponent,
    FaaliyetAlaniDialogComponent,
    FaaliyetAlaniPopupComponent,
    FaaliyetAlaniDeletePopupComponent,
    FaaliyetAlaniDeleteDialogComponent,
    faaliyetAlaniRoute,
    faaliyetAlaniPopupRoute,
} from './';

const ENTITY_STATES = [
    ...faaliyetAlaniRoute,
    ...faaliyetAlaniPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FaaliyetAlaniComponent,
        FaaliyetAlaniDetailComponent,
        FaaliyetAlaniDialogComponent,
        FaaliyetAlaniDeleteDialogComponent,
        FaaliyetAlaniPopupComponent,
        FaaliyetAlaniDeletePopupComponent,
    ],
    entryComponents: [
        FaaliyetAlaniComponent,
        FaaliyetAlaniDialogComponent,
        FaaliyetAlaniPopupComponent,
        FaaliyetAlaniDeleteDialogComponent,
        FaaliyetAlaniDeletePopupComponent,
    ],
    providers: [
        FaaliyetAlaniService,
        FaaliyetAlaniPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryFaaliyetAlaniModule {}
