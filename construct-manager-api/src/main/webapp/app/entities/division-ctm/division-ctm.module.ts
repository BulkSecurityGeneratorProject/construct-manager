import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConstructManagerApiSharedModule } from 'app/shared';
import {
    DivisionCtmComponent,
    DivisionCtmDetailComponent,
    DivisionCtmUpdateComponent,
    DivisionCtmDeletePopupComponent,
    DivisionCtmDeleteDialogComponent,
    divisionRoute,
    divisionPopupRoute
} from './';

const ENTITY_STATES = [...divisionRoute, ...divisionPopupRoute];

@NgModule({
    imports: [ConstructManagerApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DivisionCtmComponent,
        DivisionCtmDetailComponent,
        DivisionCtmUpdateComponent,
        DivisionCtmDeleteDialogComponent,
        DivisionCtmDeletePopupComponent
    ],
    entryComponents: [DivisionCtmComponent, DivisionCtmUpdateComponent, DivisionCtmDeleteDialogComponent, DivisionCtmDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConstructManagerApiDivisionCtmModule {}
