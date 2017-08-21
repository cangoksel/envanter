import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    UretimBilgisiService,
    UretimBilgisiPopupService,
    UretimBilgisiComponent,
    UretimBilgisiDetailComponent,
    UretimBilgisiDialogComponent,
    UretimBilgisiPopupComponent,
    UretimBilgisiDeletePopupComponent,
    UretimBilgisiDeleteDialogComponent,
    uretimBilgisiRoute,
    uretimBilgisiPopupRoute,
} from './';

const ENTITY_STATES = [
    ...uretimBilgisiRoute,
    ...uretimBilgisiPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UretimBilgisiComponent,
        UretimBilgisiDetailComponent,
        UretimBilgisiDialogComponent,
        UretimBilgisiDeleteDialogComponent,
        UretimBilgisiPopupComponent,
        UretimBilgisiDeletePopupComponent,
    ],
    entryComponents: [
        UretimBilgisiComponent,
        UretimBilgisiDialogComponent,
        UretimBilgisiPopupComponent,
        UretimBilgisiDeleteDialogComponent,
        UretimBilgisiDeletePopupComponent,
    ],
    providers: [
        UretimBilgisiService,
        UretimBilgisiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryUretimBilgisiModule {}
