import { BaseEntity } from './../../shared';

export class NaceKodu implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public kod?: string,
    ) {
        this.deleted = false;
    }
}
