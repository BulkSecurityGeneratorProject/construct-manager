import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRoomCtm } from 'app/shared/model/room-ctm.model';

type EntityResponseType = HttpResponse<IRoomCtm>;
type EntityArrayResponseType = HttpResponse<IRoomCtm[]>;

@Injectable({ providedIn: 'root' })
export class RoomCtmService {
    public resourceUrl = SERVER_API_URL + 'api/rooms';

    constructor(protected http: HttpClient) {}

    create(room: IRoomCtm): Observable<EntityResponseType> {
        return this.http.post<IRoomCtm>(this.resourceUrl, room, { observe: 'response' });
    }

    update(room: IRoomCtm): Observable<EntityResponseType> {
        return this.http.put<IRoomCtm>(this.resourceUrl, room, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRoomCtm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRoomCtm[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
