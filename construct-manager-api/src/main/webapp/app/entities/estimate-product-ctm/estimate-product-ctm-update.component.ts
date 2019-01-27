import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEstimateProductCtm } from 'app/shared/model/estimate-product-ctm.model';
import { EstimateProductCtmService } from './estimate-product-ctm.service';
import { IEstimateCtm } from 'app/shared/model/estimate-ctm.model';
import { EstimateCtmService } from 'app/entities/estimate-ctm';
import { IProductCtm } from 'app/shared/model/product-ctm.model';
import { ProductCtmService } from 'app/entities/product-ctm';
import { IGenericProductCtm } from 'app/shared/model/generic-product-ctm.model';
import { GenericProductCtmService } from 'app/entities/generic-product-ctm';

@Component({
    selector: 'jhi-estimate-product-ctm-update',
    templateUrl: './estimate-product-ctm-update.component.html'
})
export class EstimateProductCtmUpdateComponent implements OnInit {
    estimateProduct: IEstimateProductCtm;
    isSaving: boolean;

    estimates: IEstimateCtm[];

    products: IProductCtm[];

    genericproducts: IGenericProductCtm[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected estimateProductService: EstimateProductCtmService,
        protected estimateService: EstimateCtmService,
        protected productService: ProductCtmService,
        protected genericProductService: GenericProductCtmService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ estimateProduct }) => {
            this.estimateProduct = estimateProduct;
        });
        this.estimateService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEstimateCtm[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEstimateCtm[]>) => response.body)
            )
            .subscribe((res: IEstimateCtm[]) => (this.estimates = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductCtm[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductCtm[]>) => response.body)
            )
            .subscribe((res: IProductCtm[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.genericProductService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IGenericProductCtm[]>) => mayBeOk.ok),
                map((response: HttpResponse<IGenericProductCtm[]>) => response.body)
            )
            .subscribe((res: IGenericProductCtm[]) => (this.genericproducts = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.estimateProduct.id !== undefined) {
            this.subscribeToSaveResponse(this.estimateProductService.update(this.estimateProduct));
        } else {
            this.subscribeToSaveResponse(this.estimateProductService.create(this.estimateProduct));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstimateProductCtm>>) {
        result.subscribe((res: HttpResponse<IEstimateProductCtm>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEstimateById(index: number, item: IEstimateCtm) {
        return item.id;
    }

    trackProductById(index: number, item: IProductCtm) {
        return item.id;
    }

    trackGenericProductById(index: number, item: IGenericProductCtm) {
        return item.id;
    }
}
