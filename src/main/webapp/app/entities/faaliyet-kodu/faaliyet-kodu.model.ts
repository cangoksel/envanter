import { BaseEntity } from './../../shared';

export class FaaliyetKodu implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public faaliyetKodu?: string,
    ) {
        this.deleted = false;
    }
}
