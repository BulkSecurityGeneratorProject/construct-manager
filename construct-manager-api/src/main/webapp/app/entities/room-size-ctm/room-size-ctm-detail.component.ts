import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoomSizeCtm } from 'app/shared/model/room-size-ctm.model';

@Component({
    selector: 'jhi-room-size-ctm-detail',
    templateUrl: './room-size-ctm-detail.component.html'
})
export class RoomSizeCtmDetailComponent implements OnInit {
    roomSize: IRoomSizeCtm;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roomSize }) => {
            this.roomSize = roomSize;
        });
    }

    previousState() {
        window.history.back();
    }
}
