import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoomGenericProductCtm } from 'app/shared/model/room-generic-product-ctm.model';

@Component({
    selector: 'jhi-room-generic-product-ctm-detail',
    templateUrl: './room-generic-product-ctm-detail.component.html'
})
export class RoomGenericProductCtmDetailComponent implements OnInit {
    roomGenericProduct: IRoomGenericProductCtm;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roomGenericProduct }) => {
            this.roomGenericProduct = roomGenericProduct;
        });
    }

    previousState() {
        window.history.back();
    }
}
