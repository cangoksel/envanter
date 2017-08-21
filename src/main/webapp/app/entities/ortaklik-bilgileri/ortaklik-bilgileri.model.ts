import { BaseEntity } from './../../shared';

export class OrtaklikBilgileri implements BaseEntity {
    constructor(
        public id?: number,
        public deleted?: boolean,
        public version?: number,
        public isbirligiSunmaIstegiVarMi?: boolean,
        public isbirligiTalebiVarMi?: boolean,
        public isbirligiKonsu?: string,
        public isbirligiYapilanFirmas?: BaseEntity[],
    ) {
        this.deleted = false;
        this.isbirligiSunmaIstegiVarMi = false;
        this.isbirligiTalebiVarMi = false;
    }
}
