import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConstructManagerApiSharedModule } from 'app/shared';
import {
    RoomGenericProductCtmComponent,
    RoomGenericProductCtmDetailComponent,
    RoomGenericProductCtmUpdateComponent,
    RoomGenericProductCtmDeletePopupComponent,
    RoomGenericProductCtmDeleteDialogComponent,
    roomGenericProductRoute,
    roomGenericProductPopupRoute
} from './';

const ENTITY_STATES = [...roomGenericProductRoute, ...roomGenericProductPopupRoute];

@NgModule({
    imports: [ConstructManagerApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RoomGenericProductCtmComponent,
        RoomGenericProductCtmDetailComponent,
        RoomGenericProductCtmUpdateComponent,
        RoomGenericProductCtmDeleteDialogComponent,
        RoomGenericProductCtmDeletePopupComponent
    ],
    entryComponents: [
        RoomGenericProductCtmComponent,
        RoomGenericProductCtmUpdateComponent,
        RoomGenericProductCtmDeleteDialogComponent,
        RoomGenericProductCtmDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConstructManagerApiRoomGenericProductCtmModule {}
