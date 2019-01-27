import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRoomCtm } from 'app/shared/model/room-ctm.model';
import { RoomCtmService } from './room-ctm.service';
import { IHomeCtm } from 'app/shared/model/home-ctm.model';
import { HomeCtmService } from 'app/entities/home-ctm';
import { IRoomTypeCtm } from 'app/shared/model/room-type-ctm.model';
import { RoomTypeCtmService } from 'app/entities/room-type-ctm';

@Component({
    selector: 'jhi-room-ctm-update',
    templateUrl: './room-ctm-update.component.html'
})
export class RoomCtmUpdateComponent implements OnInit {
    room: IRoomCtm;
    isSaving: boolean;

    homes: IHomeCtm[];

    roomtypes: IRoomTypeCtm[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected roomService: RoomCtmService,
        protected homeService: HomeCtmService,
        protected roomTypeService: RoomTypeCtmService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ room }) => {
            this.room = room;
        });
        this.homeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IHomeCtm[]>) => mayBeOk.ok),
                map((response: HttpResponse<IHomeCtm[]>) => response.body)
            )
            .subscribe((res: IHomeCtm[]) => (this.homes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.roomTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRoomTypeCtm[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRoomTypeCtm[]>) => response.body)
            )
            .subscribe((res: IRoomTypeCtm[]) => (this.roomtypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.room.id !== undefined) {
            this.subscribeToSaveResponse(this.roomService.update(this.room));
        } else {
            this.subscribeToSaveResponse(this.roomService.create(this.room));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoomCtm>>) {
        result.subscribe((res: HttpResponse<IRoomCtm>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackHomeById(index: number, item: IHomeCtm) {
        return item.id;
    }

    trackRoomTypeById(index: number, item: IRoomTypeCtm) {
        return item.id;
    }
}
