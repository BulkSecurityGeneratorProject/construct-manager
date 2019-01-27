import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IShopCtm } from 'app/shared/model/shop-ctm.model';
import { ShopCtmService } from './shop-ctm.service';

@Component({
    selector: 'jhi-shop-ctm-update',
    templateUrl: './shop-ctm-update.component.html'
})
export class ShopCtmUpdateComponent implements OnInit {
    shop: IShopCtm;
    isSaving: boolean;

    constructor(protected shopService: ShopCtmService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ shop }) => {
            this.shop = shop;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.shop.id !== undefined) {
            this.subscribeToSaveResponse(this.shopService.update(this.shop));
        } else {
            this.subscribeToSaveResponse(this.shopService.create(this.shop));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IShopCtm>>) {
        result.subscribe((res: HttpResponse<IShopCtm>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
