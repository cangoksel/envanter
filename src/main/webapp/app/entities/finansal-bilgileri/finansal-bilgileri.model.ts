import { BaseEntity } from './../../shared';

export class FinansalBilgileri implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public yil?: any,
        public ihracatVarMi?: boolean,
        public yillikCiros?: BaseEntity[],
        public ihracats?: BaseEntity[],
    ) {
        this.deleted = false;
        this.ihracatVarMi = false;
    }
}
