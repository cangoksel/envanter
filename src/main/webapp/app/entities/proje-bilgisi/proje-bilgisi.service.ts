import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ProjeBilgisi } from './proje-bilgisi.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProjeBilgisiService {

    private resourceUrl = 'api/proje-bilgisis';

    constructor(private http: Http) { }

    create(projeBilgisi: ProjeBilgisi): Observable<ProjeBilgisi> {
        const copy = this.convert(projeBilgisi);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(projeBilgisi: ProjeBilgisi): Observable<ProjeBilgisi> {
        const copy = this.convert(projeBilgisi);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ProjeBilgisi> {
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

    private convert(projeBilgisi: ProjeBilgisi): ProjeBilgisi {
        const copy: ProjeBilgisi = Object.assign({}, projeBilgisi);
        return copy;
    }
}
