import { BaseEntity } from './../../shared';

export class UrunKodu implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public urunKoduAdi?: string,
        public urunKod?: string,
        public urunGrubuKodu?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
