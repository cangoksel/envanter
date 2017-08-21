/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FirmaDetailComponent } from '../../../../../../main/webapp/app/entities/firma/firma-detail.component';
import { FirmaService } from '../../../../../../main/webapp/app/entities/firma/firma.service';
import { Firma } from '../../../../../../main/webapp/app/entities/firma/firma.model';

describe('Component Tests', () => {

    describe('Firma Management Detail Component', () => {
        let comp: FirmaDetailComponent;
        let fixture: ComponentFixture<FirmaDetailComponent>;
        let service: FirmaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [FirmaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FirmaService,
                    JhiEventManager
                ]
            }).overrideTemplate(FirmaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FirmaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FirmaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Firma(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.firma).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
