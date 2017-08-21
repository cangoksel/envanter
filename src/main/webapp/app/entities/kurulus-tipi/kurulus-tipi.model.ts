import { BaseEntity } from './../../shared';

export class KurulusTipi implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public label?: string,
    ) {
        this.deleted = false;
    }
}
