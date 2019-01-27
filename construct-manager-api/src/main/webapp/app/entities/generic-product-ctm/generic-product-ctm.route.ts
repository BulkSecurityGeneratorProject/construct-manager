import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { GenericProductCtm } from 'app/shared/model/generic-product-ctm.model';
import { GenericProductCtmService } from './generic-product-ctm.service';
import { GenericProductCtmComponent } from './generic-product-ctm.component';
import { GenericProductCtmDetailComponent } from './generic-product-ctm-detail.component';
import { GenericProductCtmUpdateComponent } from './generic-product-ctm-update.component';
import { GenericProductCtmDeletePopupComponent } from './generic-product-ctm-delete-dialog.component';
import { IGenericProductCtm } from 'app/shared/model/generic-product-ctm.model';

@Injectable({ providedIn: 'root' })
export class GenericProductCtmResolve implements Resolve<IGenericProductCtm> {
    constructor(private service: GenericProductCtmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IGenericProductCtm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<GenericProductCtm>) => response.ok),
                map((genericProduct: HttpResponse<GenericProductCtm>) => genericProduct.body)
            );
        }
        return of(new GenericProductCtm());
    }
}

export const genericProductRoute: Routes = [
    {
        path: '',
        component: GenericProductCtmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'GenericProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: GenericProductCtmDetailComponent,
        resolve: {
            genericProduct: GenericProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GenericProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: GenericProductCtmUpdateComponent,
        resolve: {
            genericProduct: GenericProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GenericProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: GenericProductCtmUpdateComponent,
        resolve: {
            genericProduct: GenericProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GenericProducts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const genericProductPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: GenericProductCtmDeletePopupComponent,
        resolve: {
            genericProduct: GenericProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GenericProducts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
