import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGenericProductCtm } from 'app/shared/model/generic-product-ctm.model';

@Component({
    selector: 'jhi-generic-product-ctm-detail',
    templateUrl: './generic-product-ctm-detail.component.html'
})
export class GenericProductCtmDetailComponent implements OnInit {
    genericProduct: IGenericProductCtm;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ genericProduct }) => {
            this.genericProduct = genericProduct;
        });
    }

    previousState() {
        window.history.back();
    }
}
