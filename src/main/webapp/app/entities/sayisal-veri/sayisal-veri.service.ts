import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { SayisalVeri } from './sayisal-veri.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SayisalVeriService {

    private resourceUrl = 'api/sayisal-veris';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(sayisalVeri: SayisalVeri): Observable<SayisalVeri> {
        const copy = this.convert(sayisalVeri);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(sayisalVeri: SayisalVeri): Observable<SayisalVeri> {
        const copy = this.convert(sayisalVeri);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<SayisalVeri> {
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

    private convert(sayisalVeri: SayisalVeri): SayisalVeri {
        const copy: SayisalVeri = Object.assign({}, sayisalVeri);
        copy.yil = this.dateUtils
            .convertLocalDateToServer(sayisalVeri.yil);
        return copy;
    }
}
