import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGenericProductCategoryCtm } from 'app/shared/model/generic-product-category-ctm.model';

@Component({
    selector: 'jhi-generic-product-category-ctm-detail',
    templateUrl: './generic-product-category-ctm-detail.component.html'
})
export class GenericProductCategoryCtmDetailComponent implements OnInit {
    genericProductCategory: IGenericProductCategoryCtm;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ genericProductCategory }) => {
            this.genericProductCategory = genericProductCategory;
        });
    }

    previousState() {
        window.history.back();
    }
}
