import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Ihracat } from './ihracat.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class IhracatService {

    private resourceUrl = 'api/ihracats';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(ihracat: Ihracat): Observable<Ihracat> {
        const copy = this.convert(ihracat);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(ihracat: Ihracat): Observable<Ihracat> {
        const copy = this.convert(ihracat);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Ihracat> {
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

    private convert(ihracat: Ihracat): Ihracat {
        const copy: Ihracat = Object.assign({}, ihracat);
        copy.yil = this.dateUtils
            .convertLocalDateToServer(ihracat.yil);
        return copy;
    }
}
