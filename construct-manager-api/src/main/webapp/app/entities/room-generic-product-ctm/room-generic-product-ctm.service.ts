import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRoomGenericProductCtm } from 'app/shared/model/room-generic-product-ctm.model';

type EntityResponseType = HttpResponse<IRoomGenericProductCtm>;
type EntityArrayResponseType = HttpResponse<IRoomGenericProductCtm[]>;

@Injectable({ providedIn: 'root' })
export class RoomGenericProductCtmService {
    public resourceUrl = SERVER_API_URL + 'api/room-generic-products';

    constructor(protected http: HttpClient) {}

    create(roomGenericProduct: IRoomGenericProductCtm): Observable<EntityResponseType> {
        return this.http.post<IRoomGenericProductCtm>(this.resourceUrl, roomGenericProduct, { observe: 'response' });
    }

    update(roomGenericProduct: IRoomGenericProductCtm): Observable<EntityResponseType> {
        return this.http.put<IRoomGenericProductCtm>(this.resourceUrl, roomGenericProduct, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRoomGenericProductCtm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRoomGenericProductCtm[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
