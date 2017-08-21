import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { UrunGrubuKodu } from './urun-grubu-kodu.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UrunGrubuKoduService {

    private resourceUrl = 'api/urun-grubu-kodus';

    constructor(private http: Http) { }

    create(urunGrubuKodu: UrunGrubuKodu): Observable<UrunGrubuKodu> {
        const copy = this.convert(urunGrubuKodu);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(urunGrubuKodu: UrunGrubuKodu): Observable<UrunGrubuKodu> {
        const copy = this.convert(urunGrubuKodu);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<UrunGrubuKodu> {
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

    private convert(urunGrubuKodu: UrunGrubuKodu): UrunGrubuKodu {
        const copy: UrunGrubuKodu = Object.assign({}, urunGrubuKodu);
        return copy;
    }
}
