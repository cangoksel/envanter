import { BaseEntity } from './../../shared';

export class ProjeBilgisi implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public konusu?: string,
        public firma?: BaseEntity,
        public destekVerenKurum?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
