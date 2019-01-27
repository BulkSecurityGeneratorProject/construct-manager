import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDivisionCtm } from 'app/shared/model/division-ctm.model';

@Component({
    selector: 'jhi-division-ctm-detail',
    templateUrl: './division-ctm-detail.component.html'
})
export class DivisionCtmDetailComponent implements OnInit {
    division: IDivisionCtm;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ division }) => {
            this.division = division;
        });
    }

    previousState() {
        window.history.back();
    }
}
