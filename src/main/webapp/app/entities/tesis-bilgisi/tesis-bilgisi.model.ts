import { BaseEntity } from './../../shared';

export class TesisBilgisi implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public uretimAlani?: string,
        public firma?: BaseEntity,
        public bulunduguUlke?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
