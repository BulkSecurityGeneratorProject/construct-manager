import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRoomSizeCtm } from 'app/shared/model/room-size-ctm.model';
import { RoomSizeCtmService } from './room-size-ctm.service';
import { IRoomCtm } from 'app/shared/model/room-ctm.model';
import { RoomCtmService } from 'app/entities/room-ctm';

@Component({
    selector: 'jhi-room-size-ctm-update',
    templateUrl: './room-size-ctm-update.component.html'
})
export class RoomSizeCtmUpdateComponent implements OnInit {
    roomSize: IRoomSizeCtm;
    isSaving: boolean;

    rooms: IRoomCtm[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected roomSizeService: RoomSizeCtmService,
        protected roomService: RoomCtmService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ roomSize }) => {
            this.roomSize = roomSize;
        });
        this.roomService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRoomCtm[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRoomCtm[]>) => response.body)
            )
            .subscribe((res: IRoomCtm[]) => (this.rooms = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.roomSize.id !== undefined) {
            this.subscribeToSaveResponse(this.roomSizeService.update(this.roomSize));
        } else {
            this.subscribeToSaveResponse(this.roomSizeService.create(this.roomSize));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoomSizeCtm>>) {
        result.subscribe((res: HttpResponse<IRoomSizeCtm>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRoomById(index: number, item: IRoomCtm) {
        return item.id;
    }
}
