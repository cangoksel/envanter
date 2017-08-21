import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { UrunAltKodu } from './urun-alt-kodu.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UrunAltKoduService {

    private resourceUrl = 'api/urun-alt-kodus';

    constructor(private http: Http) { }

    create(urunAltKodu: UrunAltKodu): Observable<UrunAltKodu> {
        const copy = this.convert(urunAltKodu);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(urunAltKodu: UrunAltKodu): Observable<UrunAltKodu> {
        const copy = this.convert(urunAltKodu);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<UrunAltKodu> {
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

    private convert(urunAltKodu: UrunAltKodu): UrunAltKodu {
        const copy: UrunAltKodu = Object.assign({}, urunAltKodu);
        return copy;
    }
}
