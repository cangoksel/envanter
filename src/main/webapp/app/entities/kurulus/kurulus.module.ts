import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    KurulusService,
    KurulusPopupService,
    KurulusComponent,
    KurulusDetailComponent,
    KurulusDialogComponent,
    KurulusPopupComponent,
    KurulusDeletePopupComponent,
    KurulusDeleteDialogComponent,
    kurulusRoute,
    kurulusPopupRoute,
} from './';

const ENTITY_STATES = [
    ...kurulusRoute,
    ...kurulusPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KurulusComponent,
        KurulusDetailComponent,
        KurulusDialogComponent,
        KurulusDeleteDialogComponent,
        KurulusPopupComponent,
        KurulusDeletePopupComponent,
    ],
    entryComponents: [
        KurulusComponent,
        KurulusDialogComponent,
        KurulusPopupComponent,
        KurulusDeleteDialogComponent,
        KurulusDeletePopupComponent,
    ],
    providers: [
        KurulusService,
        KurulusPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryKurulusModule {}
