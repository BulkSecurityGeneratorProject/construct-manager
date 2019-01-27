import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IGenericProductCategoryCtm } from 'app/shared/model/generic-product-category-ctm.model';
import { GenericProductCategoryCtmService } from './generic-product-category-ctm.service';

@Component({
    selector: 'jhi-generic-product-category-ctm-update',
    templateUrl: './generic-product-category-ctm-update.component.html'
})
export class GenericProductCategoryCtmUpdateComponent implements OnInit {
    genericProductCategory: IGenericProductCategoryCtm;
    isSaving: boolean;

    constructor(protected genericProductCategoryService: GenericProductCategoryCtmService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ genericProductCategory }) => {
            this.genericProductCategory = genericProductCategory;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.genericProductCategory.id !== undefined) {
            this.subscribeToSaveResponse(this.genericProductCategoryService.update(this.genericProductCategory));
        } else {
            this.subscribeToSaveResponse(this.genericProductCategoryService.create(this.genericProductCategory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IGenericProductCategoryCtm>>) {
        result.subscribe(
            (res: HttpResponse<IGenericProductCategoryCtm>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
