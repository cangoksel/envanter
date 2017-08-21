import { BaseEntity } from './../../shared';

export class Ulke implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public label?: string,
        public harfKodu?: string,
        public kodu?: string,
        public englishName?: string,
    ) {
        this.deleted = false;
    }
}
