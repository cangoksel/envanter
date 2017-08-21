import { BaseEntity } from './../../shared';

export class TibbiCihazTehlikeSinifi implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public sinif?: string,
    ) {
        this.deleted = false;
    }
}
