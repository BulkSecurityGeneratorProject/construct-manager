import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IDivisionCtm } from 'app/shared/model/division-ctm.model';
import { DivisionCtmService } from './division-ctm.service';

@Component({
    selector: 'jhi-division-ctm-update',
    templateUrl: './division-ctm-update.component.html'
})
export class DivisionCtmUpdateComponent implements OnInit {
    division: IDivisionCtm;
    isSaving: boolean;

    constructor(protected divisionService: DivisionCtmService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ division }) => {
            this.division = division;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.division.id !== undefined) {
            this.subscribeToSaveResponse(this.divisionService.update(this.division));
        } else {
            this.subscribeToSaveResponse(this.divisionService.create(this.division));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDivisionCtm>>) {
        result.subscribe((res: HttpResponse<IDivisionCtm>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
