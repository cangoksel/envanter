import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    ProdkomKoduService,
    ProdkomKoduPopupService,
    ProdkomKoduComponent,
    ProdkomKoduDetailComponent,
    ProdkomKoduDialogComponent,
    ProdkomKoduPopupComponent,
    ProdkomKoduDeletePopupComponent,
    ProdkomKoduDeleteDialogComponent,
    prodkomKoduRoute,
    prodkomKoduPopupRoute,
} from './';

const ENTITY_STATES = [
    ...prodkomKoduRoute,
    ...prodkomKoduPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProdkomKoduComponent,
        ProdkomKoduDetailComponent,
        ProdkomKoduDialogComponent,
        ProdkomKoduDeleteDialogComponent,
        ProdkomKoduPopupComponent,
        ProdkomKoduDeletePopupComponent,
    ],
    entryComponents: [
        ProdkomKoduComponent,
        ProdkomKoduDialogComponent,
        ProdkomKoduPopupComponent,
        ProdkomKoduDeleteDialogComponent,
        ProdkomKoduDeletePopupComponent,
    ],
    providers: [
        ProdkomKoduService,
        ProdkomKoduPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryProdkomKoduModule {}
