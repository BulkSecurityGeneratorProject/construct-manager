import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEstimateCtm } from 'app/shared/model/estimate-ctm.model';
import { EstimateCtmService } from './estimate-ctm.service';
import { IDivisionCtm } from 'app/shared/model/division-ctm.model';
import { DivisionCtmService } from 'app/entities/division-ctm';

@Component({
    selector: 'jhi-estimate-ctm-update',
    templateUrl: './estimate-ctm-update.component.html'
})
export class EstimateCtmUpdateComponent implements OnInit {
    estimate: IEstimateCtm;
    isSaving: boolean;

    divisions: IDivisionCtm[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected estimateService: EstimateCtmService,
        protected divisionService: DivisionCtmService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ estimate }) => {
            this.estimate = estimate;
        });
        this.divisionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDivisionCtm[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDivisionCtm[]>) => response.body)
            )
            .subscribe((res: IDivisionCtm[]) => (this.divisions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.estimate.id !== undefined) {
            this.subscribeToSaveResponse(this.estimateService.update(this.estimate));
        } else {
            this.subscribeToSaveResponse(this.estimateService.create(this.estimate));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstimateCtm>>) {
        result.subscribe((res: HttpResponse<IEstimateCtm>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDivisionById(index: number, item: IDivisionCtm) {
        return item.id;
    }
}
