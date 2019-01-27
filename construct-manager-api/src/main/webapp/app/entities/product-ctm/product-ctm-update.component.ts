import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductCtm } from 'app/shared/model/product-ctm.model';
import { ProductCtmService } from './product-ctm.service';
import { IShopCtm } from 'app/shared/model/shop-ctm.model';
import { ShopCtmService } from 'app/entities/shop-ctm';

@Component({
    selector: 'jhi-product-ctm-update',
    templateUrl: './product-ctm-update.component.html'
})
export class ProductCtmUpdateComponent implements OnInit {
    product: IProductCtm;
    isSaving: boolean;

    shops: IShopCtm[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productService: ProductCtmService,
        protected shopService: ShopCtmService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ product }) => {
            this.product = product;
        });
        this.shopService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IShopCtm[]>) => mayBeOk.ok),
                map((response: HttpResponse<IShopCtm[]>) => response.body)
            )
            .subscribe((res: IShopCtm[]) => (this.shops = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.product.id !== undefined) {
            this.subscribeToSaveResponse(this.productService.update(this.product));
        } else {
            this.subscribeToSaveResponse(this.productService.create(this.product));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductCtm>>) {
        result.subscribe((res: HttpResponse<IProductCtm>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackShopById(index: number, item: IShopCtm) {
        return item.id;
    }
}
