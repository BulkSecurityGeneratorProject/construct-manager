import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConstructManagerApiSharedModule } from 'app/shared';
import {
    EstimateProductCtmComponent,
    EstimateProductCtmDetailComponent,
    EstimateProductCtmUpdateComponent,
    EstimateProductCtmDeletePopupComponent,
    EstimateProductCtmDeleteDialogComponent,
    estimateProductRoute,
    estimateProductPopupRoute
} from './';

const ENTITY_STATES = [...estimateProductRoute, ...estimateProductPopupRoute];

@NgModule({
    imports: [ConstructManagerApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EstimateProductCtmComponent,
        EstimateProductCtmDetailComponent,
        EstimateProductCtmUpdateComponent,
        EstimateProductCtmDeleteDialogComponent,
        EstimateProductCtmDeletePopupComponent
    ],
    entryComponents: [
        EstimateProductCtmComponent,
        EstimateProductCtmUpdateComponent,
        EstimateProductCtmDeleteDialogComponent,
        EstimateProductCtmDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConstructManagerApiEstimateProductCtmModule {}
