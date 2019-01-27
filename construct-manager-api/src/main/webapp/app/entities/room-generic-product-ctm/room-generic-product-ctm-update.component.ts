import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRoomGenericProductCtm } from 'app/shared/model/room-generic-product-ctm.model';
import { RoomGenericProductCtmService } from './room-generic-product-ctm.service';
import { IRoomCtm } from 'app/shared/model/room-ctm.model';
import { RoomCtmService } from 'app/entities/room-ctm';
import { IGenericProductCtm } from 'app/shared/model/generic-product-ctm.model';
import { GenericProductCtmService } from 'app/entities/generic-product-ctm';

@Component({
    selector: 'jhi-room-generic-product-ctm-update',
    templateUrl: './room-generic-product-ctm-update.component.html'
})
export class RoomGenericProductCtmUpdateComponent implements OnInit {
    roomGenericProduct: IRoomGenericProductCtm;
    isSaving: boolean;

    rooms: IRoomCtm[];

    genericproducts: IGenericProductCtm[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected roomGenericProductService: RoomGenericProductCtmService,
        protected roomService: RoomCtmService,
        protected genericProductService: GenericProductCtmService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ roomGenericProduct }) => {
            this.roomGenericProduct = roomGenericProduct;
        });
        this.roomService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRoomCtm[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRoomCtm[]>) => response.body)
            )
            .subscribe((res: IRoomCtm[]) => (this.rooms = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.genericProductService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IGenericProductCtm[]>) => mayBeOk.ok),
                map((response: HttpResponse<IGenericProductCtm[]>) => response.body)
            )
            .subscribe((res: IGenericProductCtm[]) => (this.genericproducts = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.roomGenericProduct.id !== undefined) {
            this.subscribeToSaveResponse(this.roomGenericProductService.update(this.roomGenericProduct));
        } else {
            this.subscribeToSaveResponse(this.roomGenericProductService.create(this.roomGenericProduct));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoomGenericProductCtm>>) {
        result.subscribe(
            (res: HttpResponse<IRoomGenericProductCtm>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackGenericProductById(index: number, item: IGenericProductCtm) {
        return item.id;
    }
}
