import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    CalisanSayiBilgisiService,
    CalisanSayiBilgisiPopupService,
    CalisanSayiBilgisiComponent,
    CalisanSayiBilgisiDetailComponent,
    CalisanSayiBilgisiDialogComponent,
    CalisanSayiBilgisiPopupComponent,
    CalisanSayiBilgisiDeletePopupComponent,
    CalisanSayiBilgisiDeleteDialogComponent,
    calisanSayiBilgisiRoute,
    calisanSayiBilgisiPopupRoute,
} from './';

const ENTITY_STATES = [
    ...calisanSayiBilgisiRoute,
    ...calisanSayiBilgisiPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CalisanSayiBilgisiComponent,
        CalisanSayiBilgisiDetailComponent,
        CalisanSayiBilgisiDialogComponent,
        CalisanSayiBilgisiDeleteDialogComponent,
        CalisanSayiBilgisiPopupComponent,
        CalisanSayiBilgisiDeletePopupComponent,
    ],
    entryComponents: [
        CalisanSayiBilgisiComponent,
        CalisanSayiBilgisiDialogComponent,
        CalisanSayiBilgisiPopupComponent,
        CalisanSayiBilgisiDeleteDialogComponent,
        CalisanSayiBilgisiDeletePopupComponent,
    ],
    providers: [
        CalisanSayiBilgisiService,
        CalisanSayiBilgisiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryCalisanSayiBilgisiModule {}
