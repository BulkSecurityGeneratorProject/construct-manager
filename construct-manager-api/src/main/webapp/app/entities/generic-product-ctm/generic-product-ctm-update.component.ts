import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IGenericProductCtm } from 'app/shared/model/generic-product-ctm.model';
import { GenericProductCtmService } from './generic-product-ctm.service';
import { IGenericProductCategoryCtm } from 'app/shared/model/generic-product-category-ctm.model';
import { GenericProductCategoryCtmService } from 'app/entities/generic-product-category-ctm';

@Component({
    selector: 'jhi-generic-product-ctm-update',
    templateUrl: './generic-product-ctm-update.component.html'
})
export class GenericProductCtmUpdateComponent implements OnInit {
    genericProduct: IGenericProductCtm;
    isSaving: boolean;

    genericproductcategories: IGenericProductCategoryCtm[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected genericProductService: GenericProductCtmService,
        protected genericProductCategoryService: GenericProductCategoryCtmService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ genericProduct }) => {
            this.genericProduct = genericProduct;
        });
        this.genericProductCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IGenericProductCategoryCtm[]>) => mayBeOk.ok),
                map((response: HttpResponse<IGenericProductCategoryCtm[]>) => response.body)
            )
            .subscribe(
                (res: IGenericProductCategoryCtm[]) => (this.genericproductcategories = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.genericProduct.id !== undefined) {
            this.subscribeToSaveResponse(this.genericProductService.update(this.genericProduct));
        } else {
            this.subscribeToSaveResponse(this.genericProductService.create(this.genericProduct));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IGenericProductCtm>>) {
        result.subscribe((res: HttpResponse<IGenericProductCtm>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGenericProductCategoryById(index: number, item: IGenericProductCategoryCtm) {
        return item.id;
    }
}
