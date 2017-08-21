import { BaseEntity } from './../../shared';

export class Adres implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public acikAdres?: string,
        public il?: BaseEntity,
        public ulke?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
