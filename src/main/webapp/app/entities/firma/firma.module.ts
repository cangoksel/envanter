import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    FirmaService,
    FirmaPopupService,
    FirmaComponent,
    FirmaDetailComponent,
    FirmaDialogComponent,
    FirmaPopupComponent,
    FirmaDeletePopupComponent,
    FirmaDeleteDialogComponent,
    firmaRoute,
    firmaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...firmaRoute,
    ...firmaPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FirmaComponent,
        FirmaDetailComponent,
        FirmaDialogComponent,
        FirmaDeleteDialogComponent,
        FirmaPopupComponent,
        FirmaDeletePopupComponent,
    ],
    entryComponents: [
        FirmaComponent,
        FirmaDialogComponent,
        FirmaPopupComponent,
        FirmaDeleteDialogComponent,
        FirmaDeletePopupComponent,
    ],
    providers: [
        FirmaService,
        FirmaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryFirmaModule {}
