import { BaseEntity } from './../../shared';

export class CalisanSayiBilgisi implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public kisiSayisi?: number,
        public isyeriBilgileri?: BaseEntity,
        public calisanTipi?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
