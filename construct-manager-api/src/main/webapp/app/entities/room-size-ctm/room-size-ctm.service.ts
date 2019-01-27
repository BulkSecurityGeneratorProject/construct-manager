import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRoomSizeCtm } from 'app/shared/model/room-size-ctm.model';

type EntityResponseType = HttpResponse<IRoomSizeCtm>;
type EntityArrayResponseType = HttpResponse<IRoomSizeCtm[]>;

@Injectable({ providedIn: 'root' })
export class RoomSizeCtmService {
    public resourceUrl = SERVER_API_URL + 'api/room-sizes';

    constructor(protected http: HttpClient) {}

    create(roomSize: IRoomSizeCtm): Observable<EntityResponseType> {
        return this.http.post<IRoomSizeCtm>(this.resourceUrl, roomSize, { observe: 'response' });
    }

    update(roomSize: IRoomSizeCtm): Observable<EntityResponseType> {
        return this.http.put<IRoomSizeCtm>(this.resourceUrl, roomSize, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRoomSizeCtm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRoomSizeCtm[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
