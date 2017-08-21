/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UrunKoduDetailComponent } from '../../../../../../main/webapp/app/entities/urun-kodu/urun-kodu-detail.component';
import { UrunKoduService } from '../../../../../../main/webapp/app/entities/urun-kodu/urun-kodu.service';
import { UrunKodu } from '../../../../../../main/webapp/app/entities/urun-kodu/urun-kodu.model';

describe('Component Tests', () => {

    describe('UrunKodu Management Detail Component', () => {
        let comp: UrunKoduDetailComponent;
        let fixture: ComponentFixture<UrunKoduDetailComponent>;
        let service: UrunKoduService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [UrunKoduDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UrunKoduService,
                    JhiEventManager
                ]
            }).overrideTemplate(UrunKoduDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UrunKoduDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UrunKoduService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UrunKodu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.urunKodu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
