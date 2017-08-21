import { BaseEntity } from './../../shared';

export class IsbirligiFirma implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public firmaAdi?: string,
        public isbirligiKonsu?: string,
        public ortaklikBilgileri?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
