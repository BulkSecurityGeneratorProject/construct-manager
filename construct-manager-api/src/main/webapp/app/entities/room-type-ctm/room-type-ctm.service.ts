import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRoomTypeCtm } from 'app/shared/model/room-type-ctm.model';

type EntityResponseType = HttpResponse<IRoomTypeCtm>;
type EntityArrayResponseType = HttpResponse<IRoomTypeCtm[]>;

@Injectable({ providedIn: 'root' })
export class RoomTypeCtmService {
    public resourceUrl = SERVER_API_URL + 'api/room-types';

    constructor(protected http: HttpClient) {}

    create(roomType: IRoomTypeCtm): Observable<EntityResponseType> {
        return this.http.post<IRoomTypeCtm>(this.resourceUrl, roomType, { observe: 'response' });
    }

    update(roomType: IRoomTypeCtm): Observable<EntityResponseType> {
        return this.http.put<IRoomTypeCtm>(this.resourceUrl, roomType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRoomTypeCtm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRoomTypeCtm[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
