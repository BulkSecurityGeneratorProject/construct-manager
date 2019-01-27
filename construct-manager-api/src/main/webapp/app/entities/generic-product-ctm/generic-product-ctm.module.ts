import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConstructManagerApiSharedModule } from 'app/shared';
import {
    GenericProductCtmComponent,
    GenericProductCtmDetailComponent,
    GenericProductCtmUpdateComponent,
    GenericProductCtmDeletePopupComponent,
    GenericProductCtmDeleteDialogComponent,
    genericProductRoute,
    genericProductPopupRoute
} from './';

const ENTITY_STATES = [...genericProductRoute, ...genericProductPopupRoute];

@NgModule({
    imports: [ConstructManagerApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GenericProductCtmComponent,
        GenericProductCtmDetailComponent,
        GenericProductCtmUpdateComponent,
        GenericProductCtmDeleteDialogComponent,
        GenericProductCtmDeletePopupComponent
    ],
    entryComponents: [
        GenericProductCtmComponent,
        GenericProductCtmUpdateComponent,
        GenericProductCtmDeleteDialogComponent,
        GenericProductCtmDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConstructManagerApiGenericProductCtmModule {}
