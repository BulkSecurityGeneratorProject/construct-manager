import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHomeCtm } from 'app/shared/model/home-ctm.model';

@Component({
    selector: 'jhi-home-ctm-detail',
    templateUrl: './home-ctm-detail.component.html'
})
export class HomeCtmDetailComponent implements OnInit {
    home: IHomeCtm;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ home }) => {
            this.home = home;
        });
    }

    previousState() {
        window.history.back();
    }
}
