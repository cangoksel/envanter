import { BaseEntity } from './../../shared';

export class UrunAltKodu implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public urunAltKoduAdi?: string,
        public urunAltKod?: string,
        public urunKodu?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
