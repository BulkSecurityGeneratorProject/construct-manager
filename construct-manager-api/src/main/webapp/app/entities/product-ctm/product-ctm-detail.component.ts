import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductCtm } from 'app/shared/model/product-ctm.model';

@Component({
    selector: 'jhi-product-ctm-detail',
    templateUrl: './product-ctm-detail.component.html'
})
export class ProductCtmDetailComponent implements OnInit {
    product: IProductCtm;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ product }) => {
            this.product = product;
        });
    }

    previousState() {
        window.history.back();
    }
}
