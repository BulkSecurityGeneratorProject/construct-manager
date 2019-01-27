import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGenericProductCategoryCtm } from 'app/shared/model/generic-product-category-ctm.model';

type EntityResponseType = HttpResponse<IGenericProductCategoryCtm>;
type EntityArrayResponseType = HttpResponse<IGenericProductCategoryCtm[]>;

@Injectable({ providedIn: 'root' })
export class GenericProductCategoryCtmService {
    public resourceUrl = SERVER_API_URL + 'api/generic-product-categories';

    constructor(protected http: HttpClient) {}

    create(genericProductCategory: IGenericProductCategoryCtm): Observable<EntityResponseType> {
        return this.http.post<IGenericProductCategoryCtm>(this.resourceUrl, genericProductCategory, { observe: 'response' });
    }

    update(genericProductCategory: IGenericProductCategoryCtm): Observable<EntityResponseType> {
        return this.http.put<IGenericProductCategoryCtm>(this.resourceUrl, genericProductCategory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGenericProductCategoryCtm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGenericProductCategoryCtm[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
