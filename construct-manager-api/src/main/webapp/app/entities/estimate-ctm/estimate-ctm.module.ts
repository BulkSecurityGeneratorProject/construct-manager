import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConstructManagerApiSharedModule } from 'app/shared';
import {
    EstimateCtmComponent,
    EstimateCtmDetailComponent,
    EstimateCtmUpdateComponent,
    EstimateCtmDeletePopupComponent,
    EstimateCtmDeleteDialogComponent,
    estimateRoute,
    estimatePopupRoute
} from './';

const ENTITY_STATES = [...estimateRoute, ...estimatePopupRoute];

@NgModule({
    imports: [ConstructManagerApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EstimateCtmComponent,
        EstimateCtmDetailComponent,
        EstimateCtmUpdateComponent,
        EstimateCtmDeleteDialogComponent,
        EstimateCtmDeletePopupComponent
    ],
    entryComponents: [EstimateCtmComponent, EstimateCtmUpdateComponent, EstimateCtmDeleteDialogComponent, EstimateCtmDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConstructManagerApiEstimateCtmModule {}
