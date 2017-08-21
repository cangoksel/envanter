import { BaseEntity } from './../../shared';

export class Ihracat implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public ithalatTutar?: number,
        public ithalatPB?: string,
        public miktar?: number,
        public yil?: any,
        public finansalBilgileri?: BaseEntity,
        public yapilanUlke?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
