import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RoomGenericProductCtm } from 'app/shared/model/room-generic-product-ctm.model';
import { RoomGenericProductCtmService } from './room-generic-product-ctm.service';
import { RoomGenericProductCtmComponent } from './room-generic-product-ctm.component';
import { RoomGenericProductCtmDetailComponent } from './room-generic-product-ctm-detail.component';
import { RoomGenericProductCtmUpdateComponent } from './room-generic-product-ctm-update.component';
import { RoomGenericProductCtmDeletePopupComponent } from './room-generic-product-ctm-delete-dialog.component';
import { IRoomGenericProductCtm } from 'app/shared/model/room-generic-product-ctm.model';

@Injectable({ providedIn: 'root' })
export class RoomGenericProductCtmResolve implements Resolve<IRoomGenericProductCtm> {
    constructor(private service: RoomGenericProductCtmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRoomGenericProductCtm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RoomGenericProductCtm>) => response.ok),
                map((roomGenericProduct: HttpResponse<RoomGenericProductCtm>) => roomGenericProduct.body)
            );
        }
        return of(new RoomGenericProductCtm());
    }
}

export const roomGenericProductRoute: Routes = [
    {
        path: '',
        component: RoomGenericProductCtmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'RoomGenericProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RoomGenericProductCtmDetailComponent,
        resolve: {
            roomGenericProduct: RoomGenericProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoomGenericProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RoomGenericProductCtmUpdateComponent,
        resolve: {
            roomGenericProduct: RoomGenericProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoomGenericProducts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RoomGenericProductCtmUpdateComponent,
        resolve: {
            roomGenericProduct: RoomGenericProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoomGenericProducts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const roomGenericProductPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RoomGenericProductCtmDeletePopupComponent,
        resolve: {
            roomGenericProduct: RoomGenericProductCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoomGenericProducts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
