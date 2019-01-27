import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RoomCtm } from 'app/shared/model/room-ctm.model';
import { RoomCtmService } from './room-ctm.service';
import { RoomCtmComponent } from './room-ctm.component';
import { RoomCtmDetailComponent } from './room-ctm-detail.component';
import { RoomCtmUpdateComponent } from './room-ctm-update.component';
import { RoomCtmDeletePopupComponent } from './room-ctm-delete-dialog.component';
import { IRoomCtm } from 'app/shared/model/room-ctm.model';

@Injectable({ providedIn: 'root' })
export class RoomCtmResolve implements Resolve<IRoomCtm> {
    constructor(private service: RoomCtmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRoomCtm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RoomCtm>) => response.ok),
                map((room: HttpResponse<RoomCtm>) => room.body)
            );
        }
        return of(new RoomCtm());
    }
}

export const roomRoute: Routes = [
    {
        path: '',
        component: RoomCtmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Rooms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RoomCtmDetailComponent,
        resolve: {
            room: RoomCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rooms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RoomCtmUpdateComponent,
        resolve: {
            room: RoomCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rooms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RoomCtmUpdateComponent,
        resolve: {
            room: RoomCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rooms'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const roomPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RoomCtmDeletePopupComponent,
        resolve: {
            room: RoomCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rooms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
