import { BaseEntity } from './../../shared';

export class Kisi implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public adi?: string,
        public soyadi?: string,
    ) {
        this.deleted = false;
    }
}
