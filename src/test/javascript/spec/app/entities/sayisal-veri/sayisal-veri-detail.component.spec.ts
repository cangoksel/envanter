/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SayisalVeriDetailComponent } from '../../../../../../main/webapp/app/entities/sayisal-veri/sayisal-veri-detail.component';
import { SayisalVeriService } from '../../../../../../main/webapp/app/entities/sayisal-veri/sayisal-veri.service';
import { SayisalVeri } from '../../../../../../main/webapp/app/entities/sayisal-veri/sayisal-veri.model';

describe('Component Tests', () => {

    describe('SayisalVeri Management Detail Component', () => {
        let comp: SayisalVeriDetailComponent;
        let fixture: ComponentFixture<SayisalVeriDetailComponent>;
        let service: SayisalVeriService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [SayisalVeriDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SayisalVeriService,
                    JhiEventManager
                ]
            }).overrideTemplate(SayisalVeriDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SayisalVeriDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SayisalVeriService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SayisalVeri(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sayisalVeri).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
