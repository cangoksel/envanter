import { BaseEntity } from './../../shared';

export class YillikCiro implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public yil?: any,
        public ciroTutar?: number,
        public ciroPB?: string,
        public finansalBilgileri?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
