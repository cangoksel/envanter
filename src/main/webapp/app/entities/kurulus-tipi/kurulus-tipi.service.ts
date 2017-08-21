import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { KurulusTipi } from './kurulus-tipi.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class KurulusTipiService {

    private resourceUrl = 'api/kurulus-tipis';

    constructor(private http: Http) { }

    create(kurulusTipi: KurulusTipi): Observable<KurulusTipi> {
        const copy = this.convert(kurulusTipi);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(kurulusTipi: KurulusTipi): Observable<KurulusTipi> {
        const copy = this.convert(kurulusTipi);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<KurulusTipi> {
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

    private convert(kurulusTipi: KurulusTipi): KurulusTipi {
        const copy: KurulusTipi = Object.assign({}, kurulusTipi);
        return copy;
    }
}
