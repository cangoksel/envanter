import { BaseEntity } from './../../shared';

export class GenelFirmaBilgileri implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public firmaUnvan?: string,
        public kurulusTarihi?: any,
        public saglikSektorundeMi?: boolean,
        public sektorBilgisi?: string,
        public argeBirimiVarMi?: boolean,
        public ticaretSicilNo?: string,
        public vergiNo?: string,
        public acikAdress?: string,
        public telefon?: string,
        public telefon2?: string,
        public fax?: string,
        public email?: string,
        public webAdresi?: string,
        public il?: BaseEntity,
        public ulke?: BaseEntity,
        public kaydiOlusturanKisi?: BaseEntity,
        public yetkiliKisi?: BaseEntity,
        public faaliyetAlani?: BaseEntity,
        public urunGrubu?: BaseEntity,
        public faaliyetKodu?: BaseEntity,
        public adres?: BaseEntity,
    ) {
        this.deleted = false;
        this.saglikSektorundeMi = false;
        this.argeBirimiVarMi = false;
    }
}
