import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { IsyeriBilgileri } from './isyeri-bilgileri.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class IsyeriBilgileriService {

    private resourceUrl = 'api/isyeri-bilgileris';

    constructor(private http: Http) { }

    create(isyeriBilgileri: IsyeriBilgileri): Observable<IsyeriBilgileri> {
        const copy = this.convert(isyeriBilgileri);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(isyeriBilgileri: IsyeriBilgileri): Observable<IsyeriBilgileri> {
        const copy = this.convert(isyeriBilgileri);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<IsyeriBilgileri> {
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

    private convert(isyeriBilgileri: IsyeriBilgileri): IsyeriBilgileri {
        const copy: IsyeriBilgileri = Object.assign({}, isyeriBilgileri);
        return copy;
    }
}
