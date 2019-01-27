import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoomCtm } from 'app/shared/model/room-ctm.model';

@Component({
    selector: 'jhi-room-ctm-detail',
    templateUrl: './room-ctm-detail.component.html'
})
export class RoomCtmDetailComponent implements OnInit {
    room: IRoomCtm;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ room }) => {
            this.room = room;
        });
    }

    previousState() {
        window.history.back();
    }
}
