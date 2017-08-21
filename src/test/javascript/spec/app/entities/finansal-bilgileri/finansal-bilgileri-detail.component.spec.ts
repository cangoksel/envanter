/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FinansalBilgileriDetailComponent } from '../../../../../../main/webapp/app/entities/finansal-bilgileri/finansal-bilgileri-detail.component';
import { FinansalBilgileriService } from '../../../../../../main/webapp/app/entities/finansal-bilgileri/finansal-bilgileri.service';
import { FinansalBilgileri } from '../../../../../../main/webapp/app/entities/finansal-bilgileri/finansal-bilgileri.model';

describe('Component Tests', () => {

    describe('FinansalBilgileri Management Detail Component', () => {
        let comp: FinansalBilgileriDetailComponent;
        let fixture: ComponentFixture<FinansalBilgileriDetailComponent>;
        let service: FinansalBilgileriService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [FinansalBilgileriDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FinansalBilgileriService,
                    JhiEventManager
                ]
            }).overrideTemplate(FinansalBilgileriDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinansalBilgileriDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinansalBilgileriService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FinansalBilgileri(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.finansalBilgileri).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
