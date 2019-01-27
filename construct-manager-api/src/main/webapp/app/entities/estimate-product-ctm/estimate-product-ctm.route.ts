import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EstimateProductCtm } from 'app/shared/model/estimate-product-ctm.model';
import { EstimateProductCtmService } from './estimate-product-ctm.service';
import { EstimateProductCtmComponent } from './estimate-product-ctm.component';
import { EstimateProductCtmDetailComponent } from './estimate-product-ctm-detail.component';
import { EstimateProductCtmUpdateComponent } from './estimate-product-ctm-update.component';
import { EstimateProductCtmDeletePopupComponent } from './estimate-product-ctm-delete-dialog.component';
import { IEstimateProductCtm } from 'app/shared/model/estimate-product-ctm.model';

@Injectable({ providedIn: 'root' })
export class EstimateProductCtmResolve implements Resolve<IEstimateProductCtm> {
    constructor(private service: EstimateProductCtmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstimateProductCtm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EstimateProductCtm>) => response.ok),
                map((estimateProduct: HttpResponse<EstimateProductCtm>) => estimateProduct.body)
            );
        }
        return of(new EstimateProductCtm());
    }
}

export const estimateProductRoute: Routes = [
    {
        path: '',
        component: EstimateProductCtmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'EstimateProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EstimateProductCtmDetailComponent,
        resolve: {
            estimateProduct: EstimateProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstimateProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EstimateProductCtmUpdateComponent,
        resolve: {
            estimateProduct: EstimateProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstimateProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EstimateProductCtmUpdateComponent,
        resolve: {
            estimateProduct: EstimateProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstimateProducts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estimateProductPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EstimateProductCtmDeletePopupComponent,
        resolve: {
            estimateProduct: EstimateProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstimateProducts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
