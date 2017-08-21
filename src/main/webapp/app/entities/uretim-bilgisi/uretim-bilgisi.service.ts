import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { UretimBilgisi } from './uretim-bilgisi.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UretimBilgisiService {

    private resourceUrl = 'api/uretim-bilgisis';

    constructor(private http: Http) { }

    create(uretimBilgisi: UretimBilgisi): Observable<UretimBilgisi> {
        const copy = this.convert(uretimBilgisi);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(uretimBilgisi: UretimBilgisi): Observable<UretimBilgisi> {
        const copy = this.convert(uretimBilgisi);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<UretimBilgisi> {
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

    private convert(uretimBilgisi: UretimBilgisi): UretimBilgisi {
        const copy: UretimBilgisi = Object.assign({}, uretimBilgisi);
        return copy;
    }
}
