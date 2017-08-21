import { BaseEntity } from './../../shared';

export class Il implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public label?: string,
        public plakaNo?: string,
        public ulke?: BaseEntity,
        public bolge?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
