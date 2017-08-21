/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { KurulusTipiDetailComponent } from '../../../../../../main/webapp/app/entities/kurulus-tipi/kurulus-tipi-detail.component';
import { KurulusTipiService } from '../../../../../../main/webapp/app/entities/kurulus-tipi/kurulus-tipi.service';
import { KurulusTipi } from '../../../../../../main/webapp/app/entities/kurulus-tipi/kurulus-tipi.model';

describe('Component Tests', () => {

    describe('KurulusTipi Management Detail Component', () => {
        let comp: KurulusTipiDetailComponent;
        let fixture: ComponentFixture<KurulusTipiDetailComponent>;
        let service: KurulusTipiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [KurulusTipiDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    KurulusTipiService,
                    JhiEventManager
                ]
            }).overrideTemplate(KurulusTipiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KurulusTipiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KurulusTipiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new KurulusTipi(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.kurulusTipi).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
