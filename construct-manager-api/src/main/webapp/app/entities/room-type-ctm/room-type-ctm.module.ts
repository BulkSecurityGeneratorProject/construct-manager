import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConstructManagerApiSharedModule } from 'app/shared';
import {
    RoomTypeCtmComponent,
    RoomTypeCtmDetailComponent,
    RoomTypeCtmUpdateComponent,
    RoomTypeCtmDeletePopupComponent,
    RoomTypeCtmDeleteDialogComponent,
    roomTypeRoute,
    roomTypePopupRoute
} from './';

const ENTITY_STATES = [...roomTypeRoute, ...roomTypePopupRoute];

@NgModule({
    imports: [ConstructManagerApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RoomTypeCtmComponent,
        RoomTypeCtmDetailComponent,
        RoomTypeCtmUpdateComponent,
        RoomTypeCtmDeleteDialogComponent,
        RoomTypeCtmDeletePopupComponent
    ],
    entryComponents: [RoomTypeCtmComponent, RoomTypeCtmUpdateComponent, RoomTypeCtmDeleteDialogComponent, RoomTypeCtmDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConstructManagerApiRoomTypeCtmModule {}
