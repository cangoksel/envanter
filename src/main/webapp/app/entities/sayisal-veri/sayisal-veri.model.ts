import { BaseEntity } from './../../shared';

export class SayisalVeri implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public yil?: any,
        public ithalatMiktari?: number,
        public ihtiyacMiktari?: number,
        public birimFiyatTutar?: number,
        public birimFiyatPB?: string,
        public dunyaPazariTutar?: number,
        public dunyaPazariPB?: string,
        public turkiyePazariTutar?: number,
        public turkiyePazariPB?: string,
        public urun?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
