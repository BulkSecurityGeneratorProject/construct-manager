import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConstructManagerApiSharedModule } from 'app/shared';
import {
    ProductCtmComponent,
    ProductCtmDetailComponent,
    ProductCtmUpdateComponent,
    ProductCtmDeletePopupComponent,
    ProductCtmDeleteDialogComponent,
    productRoute,
    productPopupRoute
} from './';

const ENTITY_STATES = [...productRoute, ...productPopupRoute];

@NgModule({
    imports: [ConstructManagerApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductCtmComponent,
        ProductCtmDetailComponent,
        ProductCtmUpdateComponent,
        ProductCtmDeleteDialogComponent,
        ProductCtmDeletePopupComponent
    ],
    entryComponents: [ProductCtmComponent, ProductCtmUpdateComponent, ProductCtmDeleteDialogComponent, ProductCtmDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConstructManagerApiProductCtmModule {}
