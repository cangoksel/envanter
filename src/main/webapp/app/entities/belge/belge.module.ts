import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventorySharedModule } from '../../shared';
import {
    BelgeService,
    BelgePopupService,
    BelgeComponent,
    BelgeDetailComponent,
    BelgeDialogComponent,
    BelgePopupComponent,
    BelgeDeletePopupComponent,
    BelgeDeleteDialogComponent,
    belgeRoute,
    belgePopupRoute,
} from './';

const ENTITY_STATES = [
    ...belgeRoute,
    ...belgePopupRoute,
];

@NgModule({
    imports: [
        InventorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BelgeComponent,
        BelgeDetailComponent,
        BelgeDialogComponent,
        BelgeDeleteDialogComponent,
        BelgePopupComponent,
        BelgeDeletePopupComponent,
    ],
    entryComponents: [
        BelgeComponent,
        BelgeDialogComponent,
        BelgePopupComponent,
        BelgeDeleteDialogComponent,
        BelgeDeletePopupComponent,
    ],
    providers: [
        BelgeService,
        BelgePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryBelgeModule {}
