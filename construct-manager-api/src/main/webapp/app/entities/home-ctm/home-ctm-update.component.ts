import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IHomeCtm } from 'app/shared/model/home-ctm.model';
import { HomeCtmService } from './home-ctm.service';

@Component({
    selector: 'jhi-home-ctm-update',
    templateUrl: './home-ctm-update.component.html'
})
export class HomeCtmUpdateComponent implements OnInit {
    home: IHomeCtm;
    isSaving: boolean;

    constructor(protected homeService: HomeCtmService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ home }) => {
            this.home = home;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.home.id !== undefined) {
            this.subscribeToSaveResponse(this.homeService.update(this.home));
        } else {
            this.subscribeToSaveResponse(this.homeService.create(this.home));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHomeCtm>>) {
        result.subscribe((res: HttpResponse<IHomeCtm>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
