import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGenericProductCtm } from 'app/shared/model/generic-product-ctm.model';

type EntityResponseType = HttpResponse<IGenericProductCtm>;
type EntityArrayResponseType = HttpResponse<IGenericProductCtm[]>;

@Injectable({ providedIn: 'root' })
export class GenericProductCtmService {
    public resourceUrl = SERVER_API_URL + 'api/generic-products';

    constructor(protected http: HttpClient) {}

    create(genericProduct: IGenericProductCtm): Observable<EntityResponseType> {
        return this.http.post<IGenericProductCtm>(this.resourceUrl, genericProduct, { observe: 'response' });
    }

    update(genericProduct: IGenericProductCtm): Observable<EntityResponseType> {
        return this.http.put<IGenericProductCtm>(this.resourceUrl, genericProduct, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGenericProductCtm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGenericProductCtm[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
