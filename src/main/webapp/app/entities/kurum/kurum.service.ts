import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Kurum } from './kurum.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class KurumService {

    private resourceUrl = 'api/kurums';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(kurum: Kurum): Observable<Kurum> {
        const copy = this.convert(kurum);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(kurum: Kurum): Observable<Kurum> {
        const copy = this.convert(kurum);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Kurum> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.yil = this.dateUtils
            .convertLocalDateFromServer(entity.yil);
    }

    private convert(kurum: Kurum): Kurum {
        const copy: Kurum = Object.assign({}, kurum);
        copy.yil = this.dateUtils
            .convertLocalDateToServer(kurum.yil);
        return copy;
    }
}
