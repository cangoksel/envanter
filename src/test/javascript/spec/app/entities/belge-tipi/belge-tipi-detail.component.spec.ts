/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BelgeTipiDetailComponent } from '../../../../../../main/webapp/app/entities/belge-tipi/belge-tipi-detail.component';
import { BelgeTipiService } from '../../../../../../main/webapp/app/entities/belge-tipi/belge-tipi.service';
import { BelgeTipi } from '../../../../../../main/webapp/app/entities/belge-tipi/belge-tipi.model';

describe('Component Tests', () => {

    describe('BelgeTipi Management Detail Component', () => {
        let comp: BelgeTipiDetailComponent;
        let fixture: ComponentFixture<BelgeTipiDetailComponent>;
        let service: BelgeTipiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [BelgeTipiDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BelgeTipiService,
                    JhiEventManager
                ]
            }).overrideTemplate(BelgeTipiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BelgeTipiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BelgeTipiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BelgeTipi(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.belgeTipi).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
