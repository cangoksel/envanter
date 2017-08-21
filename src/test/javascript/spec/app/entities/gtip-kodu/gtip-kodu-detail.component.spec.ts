/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GtipKoduDetailComponent } from '../../../../../../main/webapp/app/entities/gtip-kodu/gtip-kodu-detail.component';
import { GtipKoduService } from '../../../../../../main/webapp/app/entities/gtip-kodu/gtip-kodu.service';
import { GtipKodu } from '../../../../../../main/webapp/app/entities/gtip-kodu/gtip-kodu.model';

describe('Component Tests', () => {

    describe('GtipKodu Management Detail Component', () => {
        let comp: GtipKoduDetailComponent;
        let fixture: ComponentFixture<GtipKoduDetailComponent>;
        let service: GtipKoduService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [GtipKoduDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GtipKoduService,
                    JhiEventManager
                ]
            }).overrideTemplate(GtipKoduDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GtipKoduDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GtipKoduService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GtipKodu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.gtipKodu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
