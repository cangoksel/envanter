/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { IlDetailComponent } from '../../../../../../main/webapp/app/entities/il/il-detail.component';
import { IlService } from '../../../../../../main/webapp/app/entities/il/il.service';
import { Il } from '../../../../../../main/webapp/app/entities/il/il.model';

describe('Component Tests', () => {

    describe('Il Management Detail Component', () => {
        let comp: IlDetailComponent;
        let fixture: ComponentFixture<IlDetailComponent>;
        let service: IlService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [IlDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    IlService,
                    JhiEventManager
                ]
            }).overrideTemplate(IlDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IlDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IlService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Il(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.il).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
