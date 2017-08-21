import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    MedikalTurKoduService,
    MedikalTurKoduPopupService,
    MedikalTurKoduComponent,
    MedikalTurKoduDetailComponent,
    MedikalTurKoduDialogComponent,
    MedikalTurKoduPopupComponent,
    MedikalTurKoduDeletePopupComponent,
    MedikalTurKoduDeleteDialogComponent,
    medikalTurKoduRoute,
    medikalTurKoduPopupRoute,
} from './';

const ENTITY_STATES = [
    ...medikalTurKoduRoute,
    ...medikalTurKoduPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MedikalTurKoduComponent,
        MedikalTurKoduDetailComponent,
        MedikalTurKoduDialogComponent,
        MedikalTurKoduDeleteDialogComponent,
        MedikalTurKoduPopupComponent,
        MedikalTurKoduDeletePopupComponent,
    ],
    entryComponents: [
        MedikalTurKoduComponent,
        MedikalTurKoduDialogComponent,
        MedikalTurKoduPopupComponent,
        MedikalTurKoduDeleteDialogComponent,
        MedikalTurKoduDeletePopupComponent,
    ],
    providers: [
        MedikalTurKoduService,
        MedikalTurKoduPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryMedikalTurKoduModule {}
