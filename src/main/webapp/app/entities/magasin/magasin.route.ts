import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Magasin } from 'app/shared/model/magasin.model';
import { MagasinService } from './magasin.service';
import { MagasinComponent } from './magasin.component';
import { MagasinDetailComponent } from './magasin-detail.component';
import { MagasinUpdateComponent } from './magasin-update.component';
import { MagasinDeletePopupComponent } from './magasin-delete-dialog.component';
import { IMagasin } from 'app/shared/model/magasin.model';

@Injectable({ providedIn: 'root' })
export class MagasinResolve implements Resolve<IMagasin> {
    constructor(private service: MagasinService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMagasin> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Magasin>) => response.ok),
                map((magasin: HttpResponse<Magasin>) => magasin.body)
            );
        }
        return of(new Magasin());
    }
}

export const magasinRoute: Routes = [
    {
        path: '',
        component: MagasinComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Magasins'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MagasinDetailComponent,
        resolve: {
            magasin: MagasinResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Magasins'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MagasinUpdateComponent,
        resolve: {
            magasin: MagasinResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Magasins'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MagasinUpdateComponent,
        resolve: {
            magasin: MagasinResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Magasins'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const magasinPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MagasinDeletePopupComponent,
        resolve: {
            magasin: MagasinResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Magasins'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
