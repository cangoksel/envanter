import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CalisanTipi } from './calisan-tipi.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CalisanTipiService {

    private resourceUrl = 'api/calisan-tipis';

    constructor(private http: Http) { }

    create(calisanTipi: CalisanTipi): Observable<CalisanTipi> {
        const copy = this.convert(calisanTipi);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(calisanTipi: CalisanTipi): Observable<CalisanTipi> {
        const copy = this.convert(calisanTipi);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CalisanTipi> {
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

    private convert(calisanTipi: CalisanTipi): CalisanTipi {
        const copy: CalisanTipi = Object.assign({}, calisanTipi);
        return copy;
    }
}
