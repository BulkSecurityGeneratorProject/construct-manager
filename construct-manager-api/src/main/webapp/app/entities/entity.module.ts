import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'home-ctm',
                loadChildren: './home-ctm/home-ctm.module#ConstructManagerApiHomeCtmModule'
            },
            {
                path: 'room-ctm',
                loadChildren: './room-ctm/room-ctm.module#ConstructManagerApiRoomCtmModule'
            },
            {
                path: 'room-size-ctm',
                loadChildren: './room-size-ctm/room-size-ctm.module#ConstructManagerApiRoomSizeCtmModule'
            },
            {
                path: 'room-type-ctm',
                loadChildren: './room-type-ctm/room-type-ctm.module#ConstructManagerApiRoomTypeCtmModule'
            },
            {
                path: 'room-generic-product-ctm',
                loadChildren: './room-generic-product-ctm/room-generic-product-ctm.module#ConstructManagerApiRoomGenericProductCtmModule'
            },
            {
                path: 'generic-product-ctm',
                loadChildren: './generic-product-ctm/generic-product-ctm.module#ConstructManagerApiGenericProductCtmModule'
            },
            {
                path: 'product-ctm',
                loadChildren: './product-ctm/product-ctm.module#ConstructManagerApiProductCtmModule'
            },
            {
                path: 'division-ctm',
                loadChildren: './division-ctm/division-ctm.module#ConstructManagerApiDivisionCtmModule'
            },
            {
                path: 'generic-product-category-ctm',
                loadChildren:
                    './generic-product-category-ctm/generic-product-category-ctm.module#ConstructManagerApiGenericProductCategoryCtmModule'
            },
            {
                path: 'shop-ctm',
                loadChildren: './shop-ctm/shop-ctm.module#ConstructManagerApiShopCtmModule'
            },
            {
                path: 'estimate-ctm',
                loadChildren: './estimate-ctm/estimate-ctm.module#ConstructManagerApiEstimateCtmModule'
            },
            {
                path: 'estimate-product-ctm',
                loadChildren: './estimate-product-ctm/estimate-product-ctm.module#ConstructManagerApiEstimateProductCtmModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConstructManagerApiEntityModule {}
