import { BaseEntity } from './../../shared';

export class UrunGrubuKodu implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public urunGrubuKoduAdi?: string,
        public urunGrubuKod?: string,
    ) {
        this.deleted = false;
    }
}
