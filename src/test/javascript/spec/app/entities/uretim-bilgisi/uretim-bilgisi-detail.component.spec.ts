/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UretimBilgisiDetailComponent } from '../../../../../../main/webapp/app/entities/uretim-bilgisi/uretim-bilgisi-detail.component';
import { UretimBilgisiService } from '../../../../../../main/webapp/app/entities/uretim-bilgisi/uretim-bilgisi.service';
import { UretimBilgisi } from '../../../../../../main/webapp/app/entities/uretim-bilgisi/uretim-bilgisi.model';

describe('Component Tests', () => {

    describe('UretimBilgisi Management Detail Component', () => {
        let comp: UretimBilgisiDetailComponent;
        let fixture: ComponentFixture<UretimBilgisiDetailComponent>;
        let service: UretimBilgisiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [UretimBilgisiDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UretimBilgisiService,
                    JhiEventManager
                ]
            }).overrideTemplate(UretimBilgisiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UretimBilgisiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UretimBilgisiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UretimBilgisi(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.uretimBilgisi).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
