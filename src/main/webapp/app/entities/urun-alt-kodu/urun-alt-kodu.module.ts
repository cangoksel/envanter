import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    UrunAltKoduService,
    UrunAltKoduPopupService,
    UrunAltKoduComponent,
    UrunAltKoduDetailComponent,
    UrunAltKoduDialogComponent,
    UrunAltKoduPopupComponent,
    UrunAltKoduDeletePopupComponent,
    UrunAltKoduDeleteDialogComponent,
    urunAltKoduRoute,
    urunAltKoduPopupRoute,
} from './';

const ENTITY_STATES = [
    ...urunAltKoduRoute,
    ...urunAltKoduPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UrunAltKoduComponent,
        UrunAltKoduDetailComponent,
        UrunAltKoduDialogComponent,
        UrunAltKoduDeleteDialogComponent,
        UrunAltKoduPopupComponent,
        UrunAltKoduDeletePopupComponent,
    ],
    entryComponents: [
        UrunAltKoduComponent,
        UrunAltKoduDialogComponent,
        UrunAltKoduPopupComponent,
        UrunAltKoduDeleteDialogComponent,
        UrunAltKoduDeletePopupComponent,
    ],
    providers: [
        UrunAltKoduService,
        UrunAltKoduPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryUrunAltKoduModule {}
