/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { IhracatDetailComponent } from '../../../../../../main/webapp/app/entities/ihracat/ihracat-detail.component';
import { IhracatService } from '../../../../../../main/webapp/app/entities/ihracat/ihracat.service';
import { Ihracat } from '../../../../../../main/webapp/app/entities/ihracat/ihracat.model';

describe('Component Tests', () => {

    describe('Ihracat Management Detail Component', () => {
        let comp: IhracatDetailComponent;
        let fixture: ComponentFixture<IhracatDetailComponent>;
        let service: IhracatService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [IhracatDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    IhracatService,
                    JhiEventManager
                ]
            }).overrideTemplate(IhracatDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IhracatDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IhracatService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Ihracat(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ihracat).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
