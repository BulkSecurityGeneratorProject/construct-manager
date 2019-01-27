import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IShopCtm } from 'app/shared/model/shop-ctm.model';

type EntityResponseType = HttpResponse<IShopCtm>;
type EntityArrayResponseType = HttpResponse<IShopCtm[]>;

@Injectable({ providedIn: 'root' })
export class ShopCtmService {
    public resourceUrl = SERVER_API_URL + 'api/shops';

    constructor(protected http: HttpClient) {}

    create(shop: IShopCtm): Observable<EntityResponseType> {
        return this.http.post<IShopCtm>(this.resourceUrl, shop, { observe: 'response' });
    }

    update(shop: IShopCtm): Observable<EntityResponseType> {
        return this.http.put<IShopCtm>(this.resourceUrl, shop, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IShopCtm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IShopCtm[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
