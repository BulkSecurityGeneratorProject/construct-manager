import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HomeCtm } from 'app/shared/model/home-ctm.model';
import { HomeCtmService } from './home-ctm.service';
import { HomeCtmComponent } from './home-ctm.component';
import { HomeCtmDetailComponent } from './home-ctm-detail.component';
import { HomeCtmUpdateComponent } from './home-ctm-update.component';
import { HomeCtmDeletePopupComponent } from './home-ctm-delete-dialog.component';
import { IHomeCtm } from 'app/shared/model/home-ctm.model';

@Injectable({ providedIn: 'root' })
export class HomeCtmResolve implements Resolve<IHomeCtm> {
    constructor(private service: HomeCtmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHomeCtm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<HomeCtm>) => response.ok),
                map((home: HttpResponse<HomeCtm>) => home.body)
            );
        }
        return of(new HomeCtm());
    }
}

export const homeRoute: Routes = [
    {
        path: '',
        component: HomeCtmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Homes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HomeCtmDetailComponent,
        resolve: {
            home: HomeCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Homes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HomeCtmUpdateComponent,
        resolve: {
            home: HomeCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Homes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HomeCtmUpdateComponent,
        resolve: {
            home: HomeCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Homes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const homePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HomeCtmDeletePopupComponent,
        resolve: {
            home: HomeCtmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Homes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
