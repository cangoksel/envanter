import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { IsbirligiFirma } from './isbirligi-firma.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class IsbirligiFirmaService {

    private resourceUrl = 'api/isbirligi-firmas';

    constructor(private http: Http) { }

    create(isbirligiFirma: IsbirligiFirma): Observable<IsbirligiFirma> {
        const copy = this.convert(isbirligiFirma);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(isbirligiFirma: IsbirligiFirma): Observable<IsbirligiFirma> {
        const copy = this.convert(isbirligiFirma);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<IsbirligiFirma> {
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

    private convert(isbirligiFirma: IsbirligiFirma): IsbirligiFirma {
        const copy: IsbirligiFirma = Object.assign({}, isbirligiFirma);
        return copy;
    }
}
