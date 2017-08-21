import { BaseEntity } from './../../shared';

export class Firma implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public firmaYetkinlikCalismasiYepildiMi?: boolean,
        public genelBilgiler?: BaseEntity,
        public isyeriBilgileri?: BaseEntity,
        public ortaklikBilgileri?: BaseEntity,
        public finansalBilgileri?: BaseEntity,
        public projeBilgis?: BaseEntity[],
        public tesisBilgisis?: BaseEntity[],
        public uretimBilgisis?: BaseEntity[],
    ) {
        this.deleted = false;
        this.firmaYetkinlikCalismasiYepildiMi = false;
    }
}
