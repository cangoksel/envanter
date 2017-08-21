/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TibbiCihazTehlikeSinifiDetailComponent } from '../../../../../../main/webapp/app/entities/tibbi-cihaz-tehlike-sinifi/tibbi-cihaz-tehlike-sinifi-detail.component';
import { TibbiCihazTehlikeSinifiService } from '../../../../../../main/webapp/app/entities/tibbi-cihaz-tehlike-sinifi/tibbi-cihaz-tehlike-sinifi.service';
import { TibbiCihazTehlikeSinifi } from '../../../../../../main/webapp/app/entities/tibbi-cihaz-tehlike-sinifi/tibbi-cihaz-tehlike-sinifi.model';

describe('Component Tests', () => {

    describe('TibbiCihazTehlikeSinifi Management Detail Component', () => {
        let comp: TibbiCihazTehlikeSinifiDetailComponent;
        let fixture: ComponentFixture<TibbiCihazTehlikeSinifiDetailComponent>;
        let service: TibbiCihazTehlikeSinifiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [TibbiCihazTehlikeSinifiDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TibbiCihazTehlikeSinifiService,
                    JhiEventManager
                ]
            }).overrideTemplate(TibbiCihazTehlikeSinifiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TibbiCihazTehlikeSinifiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TibbiCihazTehlikeSinifiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TibbiCihazTehlikeSinifi(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tibbiCihazTehlikeSinifi).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
