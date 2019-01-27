import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConstructManagerApiSharedModule } from 'app/shared';
import {
    RoomSizeCtmComponent,
    RoomSizeCtmDetailComponent,
    RoomSizeCtmUpdateComponent,
    RoomSizeCtmDeletePopupComponent,
    RoomSizeCtmDeleteDialogComponent,
    roomSizeRoute,
    roomSizePopupRoute
} from './';

const ENTITY_STATES = [...roomSizeRoute, ...roomSizePopupRoute];

@NgModule({
    imports: [ConstructManagerApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RoomSizeCtmComponent,
        RoomSizeCtmDetailComponent,
        RoomSizeCtmUpdateComponent,
        RoomSizeCtmDeleteDialogComponent,
        RoomSizeCtmDeletePopupComponent
    ],
    entryComponents: [RoomSizeCtmComponent, RoomSizeCtmUpdateComponent, RoomSizeCtmDeleteDialogComponent, RoomSizeCtmDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConstructManagerApiRoomSizeCtmModule {}
