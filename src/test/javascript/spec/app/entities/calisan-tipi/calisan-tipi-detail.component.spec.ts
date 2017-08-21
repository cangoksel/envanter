/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CalisanTipiDetailComponent } from '../../../../../../main/webapp/app/entities/calisan-tipi/calisan-tipi-detail.component';
import { CalisanTipiService } from '../../../../../../main/webapp/app/entities/calisan-tipi/calisan-tipi.service';
import { CalisanTipi } from '../../../../../../main/webapp/app/entities/calisan-tipi/calisan-tipi.model';

describe('Component Tests', () => {

    describe('CalisanTipi Management Detail Component', () => {
        let comp: CalisanTipiDetailComponent;
        let fixture: ComponentFixture<CalisanTipiDetailComponent>;
        let service: CalisanTipiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [CalisanTipiDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CalisanTipiService,
                    JhiEventManager
                ]
            }).overrideTemplate(CalisanTipiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CalisanTipiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CalisanTipiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CalisanTipi(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.calisanTipi).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
