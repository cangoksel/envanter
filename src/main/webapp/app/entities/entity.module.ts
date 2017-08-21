import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { InventoryUrunGrubuKoduModule } from './urun-grubu-kodu/urun-grubu-kodu.module';
import { InventoryUrunKoduModule } from './urun-kodu/urun-kodu.module';
import { InventoryUrunAltKoduModule } from './urun-alt-kodu/urun-alt-kodu.module';
import { InventoryMedikalTurKoduModule } from './medikal-tur-kodu/medikal-tur-kodu.module';
import { InventoryMkysKoduModule } from './mkys-kodu/mkys-kodu.module';
import { InventoryActKoduModule } from './act-kodu/act-kodu.module';
import { InventoryGtipKoduModule } from './gtip-kodu/gtip-kodu.module';
import { InventoryNaceKoduModule } from './nace-kodu/nace-kodu.module';
import { InventoryTibbiCihazTehlikeSinifiModule } from './tibbi-cihaz-tehlike-sinifi/tibbi-cihaz-tehlike-sinifi.module';
import { InventoryProdkomKoduModule } from './prodkom-kodu/prodkom-kodu.module';
import { InventorySayisalVeriModule } from './sayisal-veri/sayisal-veri.module';
import { InventoryBelgeTipiModule } from './belge-tipi/belge-tipi.module';
import { InventoryBelgeModule } from './belge/belge.module';
import { InventoryUrunModule } from './urun/urun.module';
import { InventoryFirmaModule } from './firma/firma.module';
import { InventoryProjeBilgisiModule } from './proje-bilgisi/proje-bilgisi.module';
import { InventoryUretimBilgisiModule } from './uretim-bilgisi/uretim-bilgisi.module';
import { InventoryGenelFirmaBilgileriModule } from './genel-firma-bilgileri/genel-firma-bilgileri.module';
import { InventoryIsyeriBilgileriModule } from './isyeri-bilgileri/isyeri-bilgileri.module';
import { InventoryYillikCiroModule } from './yillik-ciro/yillik-ciro.module';
import { InventoryFinansalBilgileriModule } from './finansal-bilgileri/finansal-bilgileri.module';
import { InventoryKurumModule } from './kurum/kurum.module';
import { InventoryOrtaklikBilgileriModule } from './ortaklik-bilgileri/ortaklik-bilgileri.module';
import { InventoryIsbirligiFirmaModule } from './isbirligi-firma/isbirligi-firma.module';
import { InventoryFaaliyetKoduModule } from './faaliyet-kodu/faaliyet-kodu.module';
import { InventoryCalisanSayiBilgisiModule } from './calisan-sayi-bilgisi/calisan-sayi-bilgisi.module';
import { InventoryIhracatModule } from './ihracat/ihracat.module';
import { InventoryTesisBilgisiModule } from './tesis-bilgisi/tesis-bilgisi.module';
import { InventoryAdresModule } from './adres/adres.module';
import { InventoryKisiModule } from './kisi/kisi.module';
import { InventoryKurulusModule } from './kurulus/kurulus.module';
import { InventoryUrunGrubuModule } from './urun-grubu/urun-grubu.module';
import { InventoryCalisanTipiModule } from './calisan-tipi/calisan-tipi.module';
import { InventoryFaaliyetAlaniModule } from './faaliyet-alani/faaliyet-alani.module';
import { InventoryKurulusTipiModule } from './kurulus-tipi/kurulus-tipi.module';
import { InventoryUlkeModule } from './ulke/ulke.module';
import { InventoryIlModule } from './il/il.module';
import { InventoryBolgeModule } from './bolge/bolge.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        InventoryUrunGrubuKoduModule,
        InventoryUrunKoduModule,
        InventoryUrunAltKoduModule,
        InventoryMedikalTurKoduModule,
        InventoryMkysKoduModule,
        InventoryActKoduModule,
        InventoryGtipKoduModule,
        InventoryNaceKoduModule,
        InventoryTibbiCihazTehlikeSinifiModule,
        InventoryProdkomKoduModule,
        InventorySayisalVeriModule,
        InventoryBelgeTipiModule,
        InventoryBelgeModule,
        InventoryUrunModule,
        InventoryFirmaModule,
        InventoryProjeBilgisiModule,
        InventoryUretimBilgisiModule,
        InventoryGenelFirmaBilgileriModule,
        InventoryIsyeriBilgileriModule,
        InventoryYillikCiroModule,
        InventoryFinansalBilgileriModule,
        InventoryKurumModule,
        InventoryOrtaklikBilgileriModule,
        InventoryIsbirligiFirmaModule,
        InventoryFaaliyetKoduModule,
        InventoryCalisanSayiBilgisiModule,
        InventoryIhracatModule,
        InventoryTesisBilgisiModule,
        InventoryAdresModule,
        InventoryKisiModule,
        InventoryKurulusModule,
        InventoryUrunGrubuModule,
        InventoryCalisanTipiModule,
        InventoryFaaliyetAlaniModule,
        InventoryKurulusTipiModule,
        InventoryUlkeModule,
        InventoryIlModule,
        InventoryBolgeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryEntityModule {}
