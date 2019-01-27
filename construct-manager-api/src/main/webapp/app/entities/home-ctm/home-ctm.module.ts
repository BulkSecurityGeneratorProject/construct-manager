import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConstructManagerApiSharedModule } from 'app/shared';
import {
    HomeCtmComponent,
    HomeCtmDetailComponent,
    HomeCtmUpdateComponent,
    HomeCtmDeletePopupComponent,
    HomeCtmDeleteDialogComponent,
    homeRoute,
    homePopupRoute
} from './';

const ENTITY_STATES = [...homeRoute, ...homePopupRoute];

@NgModule({
    imports: [ConstructManagerApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HomeCtmComponent,
        HomeCtmDetailComponent,
        HomeCtmUpdateComponent,
        HomeCtmDeleteDialogComponent,
        HomeCtmDeletePopupComponent
    ],
    entryComponents: [HomeCtmComponent, HomeCtmUpdateComponent, HomeCtmDeleteDialogComponent, HomeCtmDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConstructManagerApiHomeCtmModule {}
