import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    UrunKoduService,
    UrunKoduPopupService,
    UrunKoduComponent,
    UrunKoduDetailComponent,
    UrunKoduDialogComponent,
    UrunKoduPopupComponent,
    UrunKoduDeletePopupComponent,
    UrunKoduDeleteDialogComponent,
    urunKoduRoute,
    urunKoduPopupRoute,
} from './';

const ENTITY_STATES = [
    ...urunKoduRoute,
    ...urunKoduPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UrunKoduComponent,
        UrunKoduDetailComponent,
        UrunKoduDialogComponent,
        UrunKoduDeleteDialogComponent,
        UrunKoduPopupComponent,
        UrunKoduDeletePopupComponent,
    ],
    entryComponents: [
        UrunKoduComponent,
        UrunKoduDialogComponent,
        UrunKoduPopupComponent,
        UrunKoduDeleteDialogComponent,
        UrunKoduDeletePopupComponent,
    ],
    providers: [
        UrunKoduService,
        UrunKoduPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryUrunKoduModule {}
