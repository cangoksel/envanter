import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { MkysKodu } from './mkys-kodu.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MkysKoduService {

    private resourceUrl = 'api/mkys-kodus';

    constructor(private http: Http) { }

    create(mkysKodu: MkysKodu): Observable<MkysKodu> {
        const copy = this.convert(mkysKodu);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(mkysKodu: MkysKodu): Observable<MkysKodu> {
        const copy = this.convert(mkysKodu);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<MkysKodu> {
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

    private convert(mkysKodu: MkysKodu): MkysKodu {
        const copy: MkysKodu = Object.assign({}, mkysKodu);
        return copy;
    }
}
