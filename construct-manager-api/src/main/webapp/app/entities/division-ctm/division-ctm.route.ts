import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DivisionCtm } from 'app/shared/model/division-ctm.model';
import { DivisionCtmService } from './division-ctm.service';
import { DivisionCtmComponent } from './division-ctm.component';
import { DivisionCtmDetailComponent } from './division-ctm-detail.component';
import { DivisionCtmUpdateComponent } from './division-ctm-update.component';
import { DivisionCtmDeletePopupComponent } from './division-ctm-delete-dialog.component';
import { IDivisionCtm } from 'app/shared/model/division-ctm.model';

@Injectable({ providedIn: 'root' })
export class DivisionCtmResolve implements Resolve<IDivisionCtm> {
    constructor(private service: DivisionCtmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDivisionCtm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DivisionCtm>) => response.ok),
                map((division: HttpResponse<DivisionCtm>) => division.body)
            );
        }
        return of(new DivisionCtm());
    }
}

export const divisionRoute: Routes = [
    {
        path: '',
        component: DivisionCtmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Divisions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DivisionCtmDetailComponent,
        resolve: {
            division: DivisionCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Divisions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DivisionCtmUpdateComponent,
        resolve: {
            division: DivisionCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Divisions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DivisionCtmUpdateComponent,
        resolve: {
            division: DivisionCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Divisions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const divisionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DivisionCtmDeletePopupComponent,
        resolve: {
            division: DivisionCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Divisions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
