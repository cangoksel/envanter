import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    ActKoduService,
    ActKoduPopupService,
    ActKoduComponent,
    ActKoduDetailComponent,
    ActKoduDialogComponent,
    ActKoduPopupComponent,
    ActKoduDeletePopupComponent,
    ActKoduDeleteDialogComponent,
    actKoduRoute,
    actKoduPopupRoute,
} from './';

const ENTITY_STATES = [
    ...actKoduRoute,
    ...actKoduPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ActKoduComponent,
        ActKoduDetailComponent,
        ActKoduDialogComponent,
        ActKoduDeleteDialogComponent,
        ActKoduPopupComponent,
        ActKoduDeletePopupComponent,
    ],
    entryComponents: [
        ActKoduComponent,
        ActKoduDialogComponent,
        ActKoduPopupComponent,
        ActKoduDeleteDialogComponent,
        ActKoduDeletePopupComponent,
    ],
    providers: [
        ActKoduService,
        ActKoduPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryActKoduModule {}
