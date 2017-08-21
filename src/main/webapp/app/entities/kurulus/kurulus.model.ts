import { BaseEntity } from './../../shared';

export class Kurulus implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public adi?: string,
        public telefon?: string,
        public telefon2?: string,
        public fax?: string,
        public email?: string,
        public webSitesi?: string,
        public uyeSayisi?: number,
        public isyeriBilgileri?: BaseEntity,
        public kurulusTipi?: BaseEntity,
        public faaliyetAlani?: BaseEntity,
        public urunGrubu?: BaseEntity,
        public yetkiliKisi?: BaseEntity,
        public adres?: BaseEntity,
        public bulunduguIl?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
