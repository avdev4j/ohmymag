import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMagasin } from 'app/shared/model/magasin.model';
import { MagasinService } from './magasin.service';
import { IDepartement } from 'app/shared/model/departement.model';
import { DepartementService } from 'app/entities/departement';

@Component({
    selector: 'jhi-magasin-update',
    templateUrl: './magasin-update.component.html'
})
export class MagasinUpdateComponent implements OnInit {
    magasin: IMagasin;
    isSaving: boolean;

    departements: IDepartement[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected magasinService: MagasinService,
        protected departementService: DepartementService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ magasin }) => {
            this.magasin = magasin;
        });
        this.departementService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDepartement[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDepartement[]>) => response.body)
            )
            .subscribe((res: IDepartement[]) => (this.departements = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.magasin.id !== undefined) {
            this.subscribeToSaveResponse(this.magasinService.update(this.magasin));
        } else {
            this.subscribeToSaveResponse(this.magasinService.create(this.magasin));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMagasin>>) {
        result.subscribe((res: HttpResponse<IMagasin>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDepartementById(index: number, item: IDepartement) {
        return item.id;
    }
}
