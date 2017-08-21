import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { FaaliyetAlani } from './faaliyet-alani.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FaaliyetAlaniService {

    private resourceUrl = 'api/faaliyet-alanis';

    constructor(private http: Http) { }

    create(faaliyetAlani: FaaliyetAlani): Observable<FaaliyetAlani> {
        const copy = this.convert(faaliyetAlani);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(faaliyetAlani: FaaliyetAlani): Observable<FaaliyetAlani> {
        const copy = this.convert(faaliyetAlani);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<FaaliyetAlani> {
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

    private convert(faaliyetAlani: FaaliyetAlani): FaaliyetAlani {
        const copy: FaaliyetAlani = Object.assign({}, faaliyetAlani);
        return copy;
    }
}
