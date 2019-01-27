import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConstructManagerApiSharedModule } from 'app/shared';
import {
    RoomCtmComponent,
    RoomCtmDetailComponent,
    RoomCtmUpdateComponent,
    RoomCtmDeletePopupComponent,
    RoomCtmDeleteDialogComponent,
    roomRoute,
    roomPopupRoute
} from './';

const ENTITY_STATES = [...roomRoute, ...roomPopupRoute];

@NgModule({
    imports: [ConstructManagerApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RoomCtmComponent,
        RoomCtmDetailComponent,
        RoomCtmUpdateComponent,
        RoomCtmDeleteDialogComponent,
        RoomCtmDeletePopupComponent
    ],
    entryComponents: [RoomCtmComponent, RoomCtmUpdateComponent, RoomCtmDeleteDialogComponent, RoomCtmDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConstructManagerApiRoomCtmModule {}
