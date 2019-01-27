import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstimateProductCtm } from 'app/shared/model/estimate-product-ctm.model';

type EntityResponseType = HttpResponse<IEstimateProductCtm>;
type EntityArrayResponseType = HttpResponse<IEstimateProductCtm[]>;

@Injectable({ providedIn: 'root' })
export class EstimateProductCtmService {
    public resourceUrl = SERVER_API_URL + 'api/estimate-products';

    constructor(protected http: HttpClient) {}

    create(estimateProduct: IEstimateProductCtm): Observable<EntityResponseType> {
        return this.http.post<IEstimateProductCtm>(this.resourceUrl, estimateProduct, { observe: 'response' });
    }

    update(estimateProduct: IEstimateProductCtm): Observable<EntityResponseType> {
        return this.http.put<IEstimateProductCtm>(this.resourceUrl, estimateProduct, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEstimateProductCtm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEstimateProductCtm[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
