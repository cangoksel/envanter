import { BaseEntity } from './../../shared';

export class Kurum implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public yil?: any,
        public adi?: string,
    ) {
        this.deleted = false;
    }
}
