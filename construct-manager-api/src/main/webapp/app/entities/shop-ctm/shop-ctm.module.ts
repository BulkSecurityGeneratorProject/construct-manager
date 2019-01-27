import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConstructManagerApiSharedModule } from 'app/shared';
import {
    ShopCtmComponent,
    ShopCtmDetailComponent,
    ShopCtmUpdateComponent,
    ShopCtmDeletePopupComponent,
    ShopCtmDeleteDialogComponent,
    shopRoute,
    shopPopupRoute
} from './';

const ENTITY_STATES = [...shopRoute, ...shopPopupRoute];

@NgModule({
    imports: [ConstructManagerApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ShopCtmComponent,
        ShopCtmDetailComponent,
        ShopCtmUpdateComponent,
        ShopCtmDeleteDialogComponent,
        ShopCtmDeletePopupComponent
    ],
    entryComponents: [ShopCtmComponent, ShopCtmUpdateComponent, ShopCtmDeleteDialogComponent, ShopCtmDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConstructManagerApiShopCtmModule {}
