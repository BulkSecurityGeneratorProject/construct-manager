import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConstructManagerApiSharedModule } from 'app/shared';
import {
    GenericProductCategoryCtmComponent,
    GenericProductCategoryCtmDetailComponent,
    GenericProductCategoryCtmUpdateComponent,
    GenericProductCategoryCtmDeletePopupComponent,
    GenericProductCategoryCtmDeleteDialogComponent,
    genericProductCategoryRoute,
    genericProductCategoryPopupRoute
} from './';

const ENTITY_STATES = [...genericProductCategoryRoute, ...genericProductCategoryPopupRoute];

@NgModule({
    imports: [ConstructManagerApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GenericProductCategoryCtmComponent,
        GenericProductCategoryCtmDetailComponent,
        GenericProductCategoryCtmUpdateComponent,
        GenericProductCategoryCtmDeleteDialogComponent,
        GenericProductCategoryCtmDeletePopupComponent
    ],
    entryComponents: [
        GenericProductCategoryCtmComponent,
        GenericProductCategoryCtmUpdateComponent,
        GenericProductCategoryCtmDeleteDialogComponent,
        GenericProductCategoryCtmDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConstructManagerApiGenericProductCategoryCtmModule {}
