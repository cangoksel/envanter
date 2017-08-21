import { BaseEntity } from './../../shared';

export class UretimBilgisi implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public firmaAciklamasi?: string,
        public firma?: BaseEntity,
        public urun?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
