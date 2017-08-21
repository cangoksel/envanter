/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FaaliyetKoduDetailComponent } from '../../../../../../main/webapp/app/entities/faaliyet-kodu/faaliyet-kodu-detail.component';
import { FaaliyetKoduService } from '../../../../../../main/webapp/app/entities/faaliyet-kodu/faaliyet-kodu.service';
import { FaaliyetKodu } from '../../../../../../main/webapp/app/entities/faaliyet-kodu/faaliyet-kodu.model';

describe('Component Tests', () => {

    describe('FaaliyetKodu Management Detail Component', () => {
        let comp: FaaliyetKoduDetailComponent;
        let fixture: ComponentFixture<FaaliyetKoduDetailComponent>;
        let service: FaaliyetKoduService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [FaaliyetKoduDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FaaliyetKoduService,
                    JhiEventManager
                ]
            }).overrideTemplate(FaaliyetKoduDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FaaliyetKoduDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FaaliyetKoduService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FaaliyetKodu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.faaliyetKodu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
