import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    UrunGrubuKoduService,
    UrunGrubuKoduPopupService,
    UrunGrubuKoduComponent,
    UrunGrubuKoduDetailComponent,
    UrunGrubuKoduDialogComponent,
    UrunGrubuKoduPopupComponent,
    UrunGrubuKoduDeletePopupComponent,
    UrunGrubuKoduDeleteDialogComponent,
    urunGrubuKoduRoute,
    urunGrubuKoduPopupRoute,
} from './';

const ENTITY_STATES = [
    ...urunGrubuKoduRoute,
    ...urunGrubuKoduPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UrunGrubuKoduComponent,
        UrunGrubuKoduDetailComponent,
        UrunGrubuKoduDialogComponent,
        UrunGrubuKoduDeleteDialogComponent,
        UrunGrubuKoduPopupComponent,
        UrunGrubuKoduDeletePopupComponent,
    ],
    entryComponents: [
        UrunGrubuKoduComponent,
        UrunGrubuKoduDialogComponent,
        UrunGrubuKoduPopupComponent,
        UrunGrubuKoduDeleteDialogComponent,
        UrunGrubuKoduDeletePopupComponent,
    ],
    providers: [
        UrunGrubuKoduService,
        UrunGrubuKoduPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryUrunGrubuKoduModule {}
