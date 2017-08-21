/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UrunAltKoduDetailComponent } from '../../../../../../main/webapp/app/entities/urun-alt-kodu/urun-alt-kodu-detail.component';
import { UrunAltKoduService } from '../../../../../../main/webapp/app/entities/urun-alt-kodu/urun-alt-kodu.service';
import { UrunAltKodu } from '../../../../../../main/webapp/app/entities/urun-alt-kodu/urun-alt-kodu.model';

describe('Component Tests', () => {

    describe('UrunAltKodu Management Detail Component', () => {
        let comp: UrunAltKoduDetailComponent;
        let fixture: ComponentFixture<UrunAltKoduDetailComponent>;
        let service: UrunAltKoduService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [UrunAltKoduDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UrunAltKoduService,
                    JhiEventManager
                ]
            }).overrideTemplate(UrunAltKoduDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UrunAltKoduDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UrunAltKoduService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UrunAltKodu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.urunAltKodu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
