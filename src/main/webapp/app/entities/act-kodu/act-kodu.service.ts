import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ActKodu } from './act-kodu.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ActKoduService {

    private resourceUrl = 'api/act-kodus';

    constructor(private http: Http) { }

    create(actKodu: ActKodu): Observable<ActKodu> {
        const copy = this.convert(actKodu);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(actKodu: ActKodu): Observable<ActKodu> {
        const copy = this.convert(actKodu);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ActKodu> {
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

    private convert(actKodu: ActKodu): ActKodu {
        const copy: ActKodu = Object.assign({}, actKodu);
        return copy;
    }
}
