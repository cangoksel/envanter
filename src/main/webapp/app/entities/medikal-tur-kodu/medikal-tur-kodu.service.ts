import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { MedikalTurKodu } from './medikal-tur-kodu.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MedikalTurKoduService {

    private resourceUrl = 'api/medikal-tur-kodus';

    constructor(private http: Http) { }

    create(medikalTurKodu: MedikalTurKodu): Observable<MedikalTurKodu> {
        const copy = this.convert(medikalTurKodu);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(medikalTurKodu: MedikalTurKodu): Observable<MedikalTurKodu> {
        const copy = this.convert(medikalTurKodu);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<MedikalTurKodu> {
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

    private convert(medikalTurKodu: MedikalTurKodu): MedikalTurKodu {
        const copy: MedikalTurKodu = Object.assign({}, medikalTurKodu);
        return copy;
    }
}
