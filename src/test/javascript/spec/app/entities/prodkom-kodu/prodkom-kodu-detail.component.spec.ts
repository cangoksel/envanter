/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProdkomKoduDetailComponent } from '../../../../../../main/webapp/app/entities/prodkom-kodu/prodkom-kodu-detail.component';
import { ProdkomKoduService } from '../../../../../../main/webapp/app/entities/prodkom-kodu/prodkom-kodu.service';
import { ProdkomKodu } from '../../../../../../main/webapp/app/entities/prodkom-kodu/prodkom-kodu.model';

describe('Component Tests', () => {

    describe('ProdkomKodu Management Detail Component', () => {
        let comp: ProdkomKoduDetailComponent;
        let fixture: ComponentFixture<ProdkomKoduDetailComponent>;
        let service: ProdkomKoduService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [ProdkomKoduDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProdkomKoduService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProdkomKoduDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProdkomKoduDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProdkomKoduService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProdkomKodu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.prodkomKodu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
