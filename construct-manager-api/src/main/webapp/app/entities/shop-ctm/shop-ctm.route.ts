import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ShopCtm } from 'app/shared/model/shop-ctm.model';
import { ShopCtmService } from './shop-ctm.service';
import { ShopCtmComponent } from './shop-ctm.component';
import { ShopCtmDetailComponent } from './shop-ctm-detail.component';
import { ShopCtmUpdateComponent } from './shop-ctm-update.component';
import { ShopCtmDeletePopupComponent } from './shop-ctm-delete-dialog.component';
import { IShopCtm } from 'app/shared/model/shop-ctm.model';

@Injectable({ providedIn: 'root' })
export class ShopCtmResolve implements Resolve<IShopCtm> {
    constructor(private service: ShopCtmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IShopCtm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ShopCtm>) => response.ok),
                map((shop: HttpResponse<ShopCtm>) => shop.body)
            );
        }
        return of(new ShopCtm());
    }
}

export const shopRoute: Routes = [
    {
        path: '',
        component: ShopCtmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Shops'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ShopCtmDetailComponent,
        resolve: {
            shop: ShopCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shops'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ShopCtmUpdateComponent,
        resolve: {
            shop: ShopCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shops'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ShopCtmUpdateComponent,
        resolve: {
            shop: ShopCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shops'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shopPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ShopCtmDeletePopupComponent,
        resolve: {
            shop: ShopCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shops'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
