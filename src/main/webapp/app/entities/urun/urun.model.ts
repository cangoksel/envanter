import { BaseEntity } from './../../shared';

export class Urun implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public tanimi?: string,
        public kullanimAlanlari?: string,
        public cesitleri?: string,
        public endikasyonlari?: string,
        public formlari?: string,
        public urunAltKodu?: BaseEntity,
        public prodkomKodu?: BaseEntity,
        public gtipKodu?: BaseEntity,
        public mkysKodu?: BaseEntity,
        public medikalTurKodu?: BaseEntity,
        public naceKodu?: BaseEntity,
        public actKodu?: BaseEntity,
        public tibbiCihazTehlikeSinifi?: BaseEntity,
        public sayisalVeris?: BaseEntity[],
        public belges?: BaseEntity[],
    ) {
        this.deleted = false;
    }
}
