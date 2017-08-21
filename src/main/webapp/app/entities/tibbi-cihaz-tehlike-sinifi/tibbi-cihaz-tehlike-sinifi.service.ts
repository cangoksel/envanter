import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { TibbiCihazTehlikeSinifi } from './tibbi-cihaz-tehlike-sinifi.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TibbiCihazTehlikeSinifiService {

    private resourceUrl = 'api/tibbi-cihaz-tehlike-sinifis';

    constructor(private http: Http) { }

    create(tibbiCihazTehlikeSinifi: TibbiCihazTehlikeSinifi): Observable<TibbiCihazTehlikeSinifi> {
        const copy = this.convert(tibbiCihazTehlikeSinifi);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(tibbiCihazTehlikeSinifi: TibbiCihazTehlikeSinifi): Observable<TibbiCihazTehlikeSinifi> {
        const copy = this.convert(tibbiCihazTehlikeSinifi);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<TibbiCihazTehlikeSinifi> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(tibbiCihazTehlikeSinifi: TibbiCihazTehlikeSinifi): TibbiCihazTehlikeSinifi {
        const copy: TibbiCihazTehlikeSinifi = Object.assign({}, tibbiCihazTehlikeSinifi);
        return copy;
    }
}
