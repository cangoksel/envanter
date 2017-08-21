import { BaseEntity } from './../../shared';

export class Belge implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public belgeAdi?: string,
        public belgeBoyutu?: number,
        public olusturmaZamani?: any,
        public silinebilirMi?: boolean,
        public aciklama?: string,
        public contentContentType?: string,
        public content?: any,
        public belgeTipi?: BaseEntity,
        public urun?: BaseEntity,
        public isyeriBilgileri?: BaseEntity,
    ) {
        this.deleted = false;
        this.silinebilirMi = false;
    }
}
