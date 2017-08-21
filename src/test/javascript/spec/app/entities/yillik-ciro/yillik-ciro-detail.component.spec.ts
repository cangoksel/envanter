/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { YillikCiroDetailComponent } from '../../../../../../main/webapp/app/entities/yillik-ciro/yillik-ciro-detail.component';
import { YillikCiroService } from '../../../../../../main/webapp/app/entities/yillik-ciro/yillik-ciro.service';
import { YillikCiro } from '../../../../../../main/webapp/app/entities/yillik-ciro/yillik-ciro.model';

describe('Component Tests', () => {

    describe('YillikCiro Management Detail Component', () => {
        let comp: YillikCiroDetailComponent;
        let fixture: ComponentFixture<YillikCiroDetailComponent>;
        let service: YillikCiroService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [YillikCiroDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    YillikCiroService,
                    JhiEventManager
                ]
            }).overrideTemplate(YillikCiroDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(YillikCiroDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(YillikCiroService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new YillikCiro(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.yillikCiro).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
