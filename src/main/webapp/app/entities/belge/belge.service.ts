import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Belge } from './belge.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BelgeService {

    private resourceUrl = 'api/belges';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(belge: Belge): Observable<Belge> {
        const copy = this.convert(belge);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(belge: Belge): Observable<Belge> {
        const copy = this.convert(belge);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Belge> {
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
        entity.olusturmaZamani = this.dateUtils
            .convertLocalDateFromServer(entity.olusturmaZamani);
    }

    private convert(belge: Belge): Belge {
        const copy: Belge = Object.assign({}, belge);
        copy.olusturmaZamani = this.dateUtils
            .convertLocalDateToServer(belge.olusturmaZamani);
        return copy;
    }
}
