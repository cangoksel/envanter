import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    TesisBilgisiService,
    TesisBilgisiPopupService,
    TesisBilgisiComponent,
    TesisBilgisiDetailComponent,
    TesisBilgisiDialogComponent,
    TesisBilgisiPopupComponent,
    TesisBilgisiDeletePopupComponent,
    TesisBilgisiDeleteDialogComponent,
    tesisBilgisiRoute,
    tesisBilgisiPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tesisBilgisiRoute,
    ...tesisBilgisiPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TesisBilgisiComponent,
        TesisBilgisiDetailComponent,
        TesisBilgisiDialogComponent,
        TesisBilgisiDeleteDialogComponent,
        TesisBilgisiPopupComponent,
        TesisBilgisiDeletePopupComponent,
    ],
    entryComponents: [
        TesisBilgisiComponent,
        TesisBilgisiDialogComponent,
        TesisBilgisiPopupComponent,
        TesisBilgisiDeleteDialogComponent,
        TesisBilgisiDeletePopupComponent,
    ],
    providers: [
        TesisBilgisiService,
        TesisBilgisiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryTesisBilgisiModule {}
