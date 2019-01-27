import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RoomTypeCtm } from 'app/shared/model/room-type-ctm.model';
import { RoomTypeCtmService } from './room-type-ctm.service';
import { RoomTypeCtmComponent } from './room-type-ctm.component';
import { RoomTypeCtmDetailComponent } from './room-type-ctm-detail.component';
import { RoomTypeCtmUpdateComponent } from './room-type-ctm-update.component';
import { RoomTypeCtmDeletePopupComponent } from './room-type-ctm-delete-dialog.component';
import { IRoomTypeCtm } from 'app/shared/model/room-type-ctm.model';

@Injectable({ providedIn: 'root' })
export class RoomTypeCtmResolve implements Resolve<IRoomTypeCtm> {
    constructor(private service: RoomTypeCtmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRoomTypeCtm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RoomTypeCtm>) => response.ok),
                map((roomType: HttpResponse<RoomTypeCtm>) => roomType.body)
            );
        }
        return of(new RoomTypeCtm());
    }
}

export const roomTypeRoute: Routes = [
    {
        path: '',
        component: RoomTypeCtmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'RoomTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RoomTypeCtmDetailComponent,
        resolve: {
            roomType: RoomTypeCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoomTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RoomTypeCtmUpdateComponent,
        resolve: {
            roomType: RoomTypeCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoomTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RoomTypeCtmUpdateComponent,
        resolve: {
            roomType: RoomTypeCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoomTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const roomTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RoomTypeCtmDeletePopupComponent,
        resolve: {
            roomType: RoomTypeCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoomTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
