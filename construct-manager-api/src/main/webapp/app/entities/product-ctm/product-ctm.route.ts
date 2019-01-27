import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductCtm } from 'app/shared/model/product-ctm.model';
import { ProductCtmService } from './product-ctm.service';
import { ProductCtmComponent } from './product-ctm.component';
import { ProductCtmDetailComponent } from './product-ctm-detail.component';
import { ProductCtmUpdateComponent } from './product-ctm-update.component';
import { ProductCtmDeletePopupComponent } from './product-ctm-delete-dialog.component';
import { IProductCtm } from 'app/shared/model/product-ctm.model';

@Injectable({ providedIn: 'root' })
export class ProductCtmResolve implements Resolve<IProductCtm> {
    constructor(private service: ProductCtmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductCtm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductCtm>) => response.ok),
                map((product: HttpResponse<ProductCtm>) => product.body)
            );
        }
        return of(new ProductCtm());
    }
}

export const productRoute: Routes = [
    {
        path: '',
        component: ProductCtmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductCtmDetailComponent,
        resolve: {
            product: ProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductCtmUpdateComponent,
        resolve: {
            product: ProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductCtmUpdateComponent,
        resolve: {
            product: ProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductCtmDeletePopupComponent,
        resolve: {
            product: ProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
