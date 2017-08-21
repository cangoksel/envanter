import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    IsbirligiFirmaService,
    IsbirligiFirmaPopupService,
    IsbirligiFirmaComponent,
    IsbirligiFirmaDetailComponent,
    IsbirligiFirmaDialogComponent,
    IsbirligiFirmaPopupComponent,
    IsbirligiFirmaDeletePopupComponent,
    IsbirligiFirmaDeleteDialogComponent,
    isbirligiFirmaRoute,
    isbirligiFirmaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...isbirligiFirmaRoute,
    ...isbirligiFirmaPopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        IsbirligiFirmaComponent,
        IsbirligiFirmaDetailComponent,
        IsbirligiFirmaDialogComponent,
        IsbirligiFirmaDeleteDialogComponent,
        IsbirligiFirmaPopupComponent,
        IsbirligiFirmaDeletePopupComponent,
    ],
    entryComponents: [
        IsbirligiFirmaComponent,
        IsbirligiFirmaDialogComponent,
        IsbirligiFirmaPopupComponent,
        IsbirligiFirmaDeleteDialogComponent,
        IsbirligiFirmaDeletePopupComponent,
    ],
    providers: [
        IsbirligiFirmaService,
        IsbirligiFirmaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryIsbirligiFirmaModule {}
