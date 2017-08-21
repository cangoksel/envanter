import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { GenelFirmaBilgileri } from './genel-firma-bilgileri.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class GenelFirmaBilgileriService {

    private resourceUrl = 'api/genel-firma-bilgileris';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(genelFirmaBilgileri: GenelFirmaBilgileri): Observable<GenelFirmaBilgileri> {
        const copy = this.convert(genelFirmaBilgileri);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(genelFirmaBilgileri: GenelFirmaBilgileri): Observable<GenelFirmaBilgileri> {
        const copy = this.convert(genelFirmaBilgileri);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<GenelFirmaBilgileri> {
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
        entity.kurulusTarihi = this.dateUtils
            .convertLocalDateFromServer(entity.kurulusTarihi);
    }

    private convert(genelFirmaBilgileri: GenelFirmaBilgileri): GenelFirmaBilgileri {
        const copy: GenelFirmaBilgileri = Object.assign({}, genelFirmaBilgileri);
        copy.kurulusTarihi = this.dateUtils
            .convertLocalDateToServer(genelFirmaBilgileri.kurulusTarihi);
        return copy;
    }
}
