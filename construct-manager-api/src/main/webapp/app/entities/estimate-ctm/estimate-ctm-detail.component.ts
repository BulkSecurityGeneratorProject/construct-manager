import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstimateCtm } from 'app/shared/model/estimate-ctm.model';

@Component({
    selector: 'jhi-estimate-ctm-detail',
    templateUrl: './estimate-ctm-detail.component.html'
})
export class EstimateCtmDetailComponent implements OnInit {
    estimate: IEstimateCtm;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estimate }) => {
            this.estimate = estimate;
        });
    }

    previousState() {
        window.history.back();
    }
}
