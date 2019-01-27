import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RoomSizeCtm } from 'app/shared/model/room-size-ctm.model';
import { RoomSizeCtmService } from './room-size-ctm.service';
import { RoomSizeCtmComponent } from './room-size-ctm.component';
import { RoomSizeCtmDetailComponent } from './room-size-ctm-detail.component';
import { RoomSizeCtmUpdateComponent } from './room-size-ctm-update.component';
import { RoomSizeCtmDeletePopupComponent } from './room-size-ctm-delete-dialog.component';
import { IRoomSizeCtm } from 'app/shared/model/room-size-ctm.model';

@Injectable({ providedIn: 'root' })
export class RoomSizeCtmResolve implements Resolve<IRoomSizeCtm> {
    constructor(private service: RoomSizeCtmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRoomSizeCtm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RoomSizeCtm>) => response.ok),
                map((roomSize: HttpResponse<RoomSizeCtm>) => roomSize.body)
            );
        }
        return of(new RoomSizeCtm());
    }
}

export const roomSizeRoute: Routes = [
    {
        path: '',
        component: RoomSizeCtmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'RoomSizes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RoomSizeCtmDetailComponent,
        resolve: {
            roomSize: RoomSizeCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoomSizes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RoomSizeCtmUpdateComponent,
        resolve: {
            roomSize: RoomSizeCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoomSizes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RoomSizeCtmUpdateComponent,
        resolve: {
            roomSize: RoomSizeCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoomSizes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const roomSizePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RoomSizeCtmDeletePopupComponent,
        resolve: {
            roomSize: RoomSizeCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoomSizes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
