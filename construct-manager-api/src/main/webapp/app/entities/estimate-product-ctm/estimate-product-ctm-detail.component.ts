import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstimateProductCtm } from 'app/shared/model/estimate-product-ctm.model';

@Component({
    selector: 'jhi-estimate-product-ctm-detail',
    templateUrl: './estimate-product-ctm-detail.component.html'
})
export class EstimateProductCtmDetailComponent implements OnInit {
    estimateProduct: IEstimateProductCtm;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estimateProduct }) => {
            this.estimateProduct = estimateProduct;
        });
    }

    previousState() {
        window.history.back();
    }
}
