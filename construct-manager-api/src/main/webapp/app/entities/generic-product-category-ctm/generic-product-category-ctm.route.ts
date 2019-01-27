import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { GenericProductCategoryCtm } from 'app/shared/model/generic-product-category-ctm.model';
import { GenericProductCategoryCtmService } from './generic-product-category-ctm.service';
import { GenericProductCategoryCtmComponent } from './generic-product-category-ctm.component';
import { GenericProductCategoryCtmDetailComponent } from './generic-product-category-ctm-detail.component';
import { GenericProductCategoryCtmUpdateComponent } from './generic-product-category-ctm-update.component';
import { GenericProductCategoryCtmDeletePopupComponent } from './generic-product-category-ctm-delete-dialog.component';
import { IGenericProductCategoryCtm } from 'app/shared/model/generic-product-category-ctm.model';

@Injectable({ providedIn: 'root' })
export class GenericProductCategoryCtmResolve implements Resolve<IGenericProductCategoryCtm> {
    constructor(private service: GenericProductCategoryCtmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IGenericProductCategoryCtm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<GenericProductCategoryCtm>) => response.ok),
                map((genericProductCategory: HttpResponse<GenericProductCategoryCtm>) => genericProductCategory.body)
            );
        }
        return of(new GenericProductCategoryCtm());
    }
}

export const genericProductCategoryRoute: Routes = [
    {
        path: '',
        component: GenericProductCategoryCtmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'GenericProductCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: GenericProductCategoryCtmDetailComponent,
        resolve: {
            genericProductCategory: GenericProductCategoryCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GenericProductCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: GenericProductCategoryCtmUpdateComponent,
        resolve: {
            genericProductCategory: GenericProductCategoryCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GenericProductCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: GenericProductCategoryCtmUpdateComponent,
        resolve: {
            genericProductCategory: GenericProductCategoryCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GenericProductCategories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const genericProductCategoryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: GenericProductCategoryCtmDeletePopupComponent,
        resolve: {
            genericProductCategory: GenericProductCategoryCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GenericProductCategories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
