import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDepartement } from 'app/shared/model/departement.model';
import { AccountService } from 'app/core';
import { DepartementService } from './departement.service';

@Component({
    selector: 'jhi-departement',
    templateUrl: './departement.component.html'
})
export class DepartementComponent implements OnInit, OnDestroy {
    departements: IDepartement[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected departementService: DepartementService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.departementService
            .query()
            .pipe(
                filter((res: HttpResponse<IDepartement[]>) => res.ok),
                map((res: HttpResponse<IDepartement[]>) => res.body)
            )
            .subscribe(
                (res: IDepartement[]) => {
                    this.departements = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDepartements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDepartement) {
        return item.id;
    }

    registerChangeInDepartements() {
        this.eventSubscriber = this.eventManager.subscribe('departementListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
