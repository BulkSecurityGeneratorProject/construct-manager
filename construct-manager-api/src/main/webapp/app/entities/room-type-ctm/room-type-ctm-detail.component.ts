import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoomTypeCtm } from 'app/shared/model/room-type-ctm.model';

@Component({
    selector: 'jhi-room-type-ctm-detail',
    templateUrl: './room-type-ctm-detail.component.html'
})
export class RoomTypeCtmDetailComponent implements OnInit {
    roomType: IRoomTypeCtm;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roomType }) => {
            this.roomType = roomType;
        });
    }

    previousState() {
        window.history.back();
    }
}
