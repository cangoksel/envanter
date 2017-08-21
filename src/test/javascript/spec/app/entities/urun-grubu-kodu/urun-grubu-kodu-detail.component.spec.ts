/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UrunGrubuKoduDetailComponent } from '../../../../../../main/webapp/app/entities/urun-grubu-kodu/urun-grubu-kodu-detail.component';
import { UrunGrubuKoduService } from '../../../../../../main/webapp/app/entities/urun-grubu-kodu/urun-grubu-kodu.service';
import { UrunGrubuKodu } from '../../../../../../main/webapp/app/entities/urun-grubu-kodu/urun-grubu-kodu.model';

describe('Component Tests', () => {

    describe('UrunGrubuKodu Management Detail Component', () => {
        let comp: UrunGrubuKoduDetailComponent;
        let fixture: ComponentFixture<UrunGrubuKoduDetailComponent>;
        let service: UrunGrubuKoduService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [UrunGrubuKoduDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UrunGrubuKoduService,
                    JhiEventManager
                ]
            }).overrideTemplate(UrunGrubuKoduDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UrunGrubuKoduDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UrunGrubuKoduService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UrunGrubuKodu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.urunGrubuKodu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
