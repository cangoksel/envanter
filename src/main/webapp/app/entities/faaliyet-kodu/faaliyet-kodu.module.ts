import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    FaaliyetKoduService,
    FaaliyetKoduPopupService,
    FaaliyetKoduComponent,
    FaaliyetKoduDetailComponent,
    FaaliyetKoduDialogComponent,
    FaaliyetKoduPopupComponent,
    FaaliyetKoduDeletePopupComponent,
    FaaliyetKoduDeleteDialogComponent,
    faaliyetKoduRoute,
    faaliyetKoduPopupRoute,
} from './';

const ENTITY_STATES = [
    ...faaliyetKoduRoute,
    ...faaliyetKoduPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FaaliyetKoduComponent,
        FaaliyetKoduDetailComponent,
        FaaliyetKoduDialogComponent,
        FaaliyetKoduDeleteDialogComponent,
        FaaliyetKoduPopupComponent,
        FaaliyetKoduDeletePopupComponent,
    ],
    entryComponents: [
        FaaliyetKoduComponent,
        FaaliyetKoduDialogComponent,
        FaaliyetKoduPopupComponent,
        FaaliyetKoduDeleteDialogComponent,
        FaaliyetKoduDeletePopupComponent,
    ],
    providers: [
        FaaliyetKoduService,
        FaaliyetKoduPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryFaaliyetKoduModule {}
