import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    UrunGrubuService,
    UrunGrubuPopupService,
    UrunGrubuComponent,
    UrunGrubuDetailComponent,
    UrunGrubuDialogComponent,
    UrunGrubuPopupComponent,
    UrunGrubuDeletePopupComponent,
    UrunGrubuDeleteDialogComponent,
    urunGrubuRoute,
    urunGrubuPopupRoute,
} from './';

const ENTITY_STATES = [
    ...urunGrubuRoute,
    ...urunGrubuPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UrunGrubuComponent,
        UrunGrubuDetailComponent,
        UrunGrubuDialogComponent,
        UrunGrubuDeleteDialogComponent,
        UrunGrubuPopupComponent,
        UrunGrubuDeletePopupComponent,
    ],
    entryComponents: [
        UrunGrubuComponent,
        UrunGrubuDialogComponent,
        UrunGrubuPopupComponent,
        UrunGrubuDeleteDialogComponent,
        UrunGrubuDeletePopupComponent,
    ],
    providers: [
        UrunGrubuService,
        UrunGrubuPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryUrunGrubuModule {}
