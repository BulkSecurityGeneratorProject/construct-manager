import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShopCtm } from 'app/shared/model/shop-ctm.model';

@Component({
    selector: 'jhi-shop-ctm-detail',
    templateUrl: './shop-ctm-detail.component.html'
})
export class ShopCtmDetailComponent implements OnInit {
    shop: IShopCtm;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ shop }) => {
            this.shop = shop;
        });
    }

    previousState() {
        window.history.back();
    }
}
