import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Ulke } from './ulke.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UlkeService {

    private resourceUrl = 'api/ulkes';

    constructor(private http: Http) { }

    create(ulke: Ulke): Observable<Ulke> {
        const copy = this.convert(ulke);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(ulke: Ulke): Observable<Ulke> {
        const copy = this.convert(ulke);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Ulke> {
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

    private convert(ulke: Ulke): Ulke {
        const copy: Ulke = Object.assign({}, ulke);
        return copy;
    }
}
