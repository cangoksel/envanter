/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FaaliyetAlaniDetailComponent } from '../../../../../../main/webapp/app/entities/faaliyet-alani/faaliyet-alani-detail.component';
import { FaaliyetAlaniService } from '../../../../../../main/webapp/app/entities/faaliyet-alani/faaliyet-alani.service';
import { FaaliyetAlani } from '../../../../../../main/webapp/app/entities/faaliyet-alani/faaliyet-alani.model';

describe('Component Tests', () => {

    describe('FaaliyetAlani Management Detail Component', () => {
        let comp: FaaliyetAlaniDetailComponent;
        let fixture: ComponentFixture<FaaliyetAlaniDetailComponent>;
        let service: FaaliyetAlaniService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [FaaliyetAlaniDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FaaliyetAlaniService,
                    JhiEventManager
                ]
            }).overrideTemplate(FaaliyetAlaniDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FaaliyetAlaniDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FaaliyetAlaniService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FaaliyetAlani(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.faaliyetAlani).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
