/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ActKoduDetailComponent } from '../../../../../../main/webapp/app/entities/act-kodu/act-kodu-detail.component';
import { ActKoduService } from '../../../../../../main/webapp/app/entities/act-kodu/act-kodu.service';
import { ActKodu } from '../../../../../../main/webapp/app/entities/act-kodu/act-kodu.model';

describe('Component Tests', () => {

    describe('ActKodu Management Detail Component', () => {
        let comp: ActKoduDetailComponent;
        let fixture: ComponentFixture<ActKoduDetailComponent>;
        let service: ActKoduService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [ActKoduDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ActKoduService,
                    JhiEventManager
                ]
            }).overrideTemplate(ActKoduDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActKoduDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActKoduService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ActKodu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.actKodu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
