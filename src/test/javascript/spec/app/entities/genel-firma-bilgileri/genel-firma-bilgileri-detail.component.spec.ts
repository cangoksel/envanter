/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GenelFirmaBilgileriDetailComponent } from '../../../../../../main/webapp/app/entities/genel-firma-bilgileri/genel-firma-bilgileri-detail.component';
import { GenelFirmaBilgileriService } from '../../../../../../main/webapp/app/entities/genel-firma-bilgileri/genel-firma-bilgileri.service';
import { GenelFirmaBilgileri } from '../../../../../../main/webapp/app/entities/genel-firma-bilgileri/genel-firma-bilgileri.model';

describe('Component Tests', () => {

    describe('GenelFirmaBilgileri Management Detail Component', () => {
        let comp: GenelFirmaBilgileriDetailComponent;
        let fixture: ComponentFixture<GenelFirmaBilgileriDetailComponent>;
        let service: GenelFirmaBilgileriService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [GenelFirmaBilgileriDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GenelFirmaBilgileriService,
                    JhiEventManager
                ]
            }).overrideTemplate(GenelFirmaBilgileriDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GenelFirmaBilgileriDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GenelFirmaBilgileriService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GenelFirmaBilgileri(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.genelFirmaBilgileri).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
