import { BaseEntity } from './../../shared';

export class IsyeriBilgileri implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public kayitliOlduguKuruluses?: BaseEntity[],
        public calisanBilgis?: BaseEntity[],
        public kaliteBelgesis?: BaseEntity[],
    ) {
        this.deleted = false;
    }
}
