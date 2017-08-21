import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    KurulusTipiService,
    KurulusTipiPopupService,
    KurulusTipiComponent,
    KurulusTipiDetailComponent,
    KurulusTipiDialogComponent,
    KurulusTipiPopupComponent,
    KurulusTipiDeletePopupComponent,
    KurulusTipiDeleteDialogComponent,
    kurulusTipiRoute,
    kurulusTipiPopupRoute,
} from './';

const ENTITY_STATES = [
    ...kurulusTipiRoute,
    ...kurulusTipiPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KurulusTipiComponent,
        KurulusTipiDetailComponent,
        KurulusTipiDialogComponent,
        KurulusTipiDeleteDialogComponent,
        KurulusTipiPopupComponent,
        KurulusTipiDeletePopupComponent,
    ],
    entryComponents: [
        KurulusTipiComponent,
        KurulusTipiDialogComponent,
        KurulusTipiPopupComponent,
        KurulusTipiDeleteDialogComponent,
        KurulusTipiDeletePopupComponent,
    ],
    providers: [
        KurulusTipiService,
        KurulusTipiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryKurulusTipiModule {}
