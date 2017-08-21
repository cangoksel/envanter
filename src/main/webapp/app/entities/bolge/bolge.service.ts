import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Bolge } from './bolge.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BolgeService {

    private resourceUrl = 'api/bolges';

    constructor(private http: Http) { }

    create(bolge: Bolge): Observable<Bolge> {
        const copy = this.convert(bolge);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(bolge: Bolge): Observable<Bolge> {
        const copy = this.convert(bolge);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Bolge> {
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

    private convert(bolge: Bolge): Bolge {
        const copy: Bolge = Object.assign({}, bolge);
        return copy;
    }
}
