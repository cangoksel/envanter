import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { GtipKodu } from './gtip-kodu.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class GtipKoduService {

    private resourceUrl = 'api/gtip-kodus';

    constructor(private http: Http) { }

    create(gtipKodu: GtipKodu): Observable<GtipKodu> {
        const copy = this.convert(gtipKodu);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(gtipKodu: GtipKodu): Observable<GtipKodu> {
        const copy = this.convert(gtipKodu);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<GtipKodu> {
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

    private convert(gtipKodu: GtipKodu): GtipKodu {
        const copy: GtipKodu = Object.assign({}, gtipKodu);
        return copy;
    }
}
