/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { NaceKoduDetailComponent } from '../../../../../../main/webapp/app/entities/nace-kodu/nace-kodu-detail.component';
import { NaceKoduService } from '../../../../../../main/webapp/app/entities/nace-kodu/nace-kodu.service';
import { NaceKodu } from '../../../../../../main/webapp/app/entities/nace-kodu/nace-kodu.model';

describe('Component Tests', () => {

    describe('NaceKodu Management Detail Component', () => {
        let comp: NaceKoduDetailComponent;
        let fixture: ComponentFixture<NaceKoduDetailComponent>;
        let service: NaceKoduService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [NaceKoduDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    NaceKoduService,
                    JhiEventManager
                ]
            }).overrideTemplate(NaceKoduDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NaceKoduDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NaceKoduService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new NaceKodu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.naceKodu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
