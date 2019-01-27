import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EstimateCtm } from 'app/shared/model/estimate-ctm.model';
import { EstimateCtmService } from './estimate-ctm.service';
import { EstimateCtmComponent } from './estimate-ctm.component';
import { EstimateCtmDetailComponent } from './estimate-ctm-detail.component';
import { EstimateCtmUpdateComponent } from './estimate-ctm-update.component';
import { EstimateCtmDeletePopupComponent } from './estimate-ctm-delete-dialog.component';
import { IEstimateCtm } from 'app/shared/model/estimate-ctm.model';

@Injectable({ providedIn: 'root' })
export class EstimateCtmResolve implements Resolve<IEstimateCtm> {
    constructor(private service: EstimateCtmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstimateCtm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EstimateCtm>) => response.ok),
                map((estimate: HttpResponse<EstimateCtm>) => estimate.body)
            );
        }
        return of(new EstimateCtm());
    }
}

export const estimateRoute: Routes = [
    {
        path: '',
        component: EstimateCtmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Estimates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EstimateCtmDetailComponent,
        resolve: {
            estimate: EstimateCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estimates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EstimateCtmUpdateComponent,
        resolve: {
            estimate: EstimateCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estimates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EstimateCtmUpdateComponent,
        resolve: {
            estimate: EstimateCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estimates'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estimatePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EstimateCtmDeletePopupComponent,
        resolve: {
            estimate: EstimateCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estimates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
