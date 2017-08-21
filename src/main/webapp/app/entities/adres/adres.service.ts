import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Adres } from './adres.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AdresService {

    private resourceUrl = 'api/adres';

    constructor(private http: Http) { }

    create(adres: Adres): Observable<Adres> {
        const copy = this.convert(adres);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(adres: Adres): Observable<Adres> {
        const copy = this.convert(adres);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Adres> {
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

    private convert(adres: Adres): Adres {
        const copy: Adres = Object.assign({}, adres);
        return copy;
    }
}
