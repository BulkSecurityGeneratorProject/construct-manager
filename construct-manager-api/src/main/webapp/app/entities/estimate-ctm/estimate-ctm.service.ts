import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstimateCtm } from 'app/shared/model/estimate-ctm.model';

type EntityResponseType = HttpResponse<IEstimateCtm>;
type EntityArrayResponseType = HttpResponse<IEstimateCtm[]>;

@Injectable({ providedIn: 'root' })
export class EstimateCtmService {
    public resourceUrl = SERVER_API_URL + 'api/estimates';

    constructor(protected http: HttpClient) {}

    create(estimate: IEstimateCtm): Observable<EntityResponseType> {
        return this.http.post<IEstimateCtm>(this.resourceUrl, estimate, { observe: 'response' });
    }

    update(estimate: IEstimateCtm): Observable<EntityResponseType> {
        return this.http.put<IEstimateCtm>(this.resourceUrl, estimate, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEstimateCtm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEstimateCtm[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
