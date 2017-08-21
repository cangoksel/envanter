/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { KurulusDetailComponent } from '../../../../../../main/webapp/app/entities/kurulus/kurulus-detail.component';
import { KurulusService } from '../../../../../../main/webapp/app/entities/kurulus/kurulus.service';
import { Kurulus } from '../../../../../../main/webapp/app/entities/kurulus/kurulus.model';

describe('Component Tests', () => {

    describe('Kurulus Management Detail Component', () => {
        let comp: KurulusDetailComponent;
        let fixture: ComponentFixture<KurulusDetailComponent>;
        let service: KurulusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [KurulusDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    KurulusService,
                    JhiEventManager
                ]
            }).overrideTemplate(KurulusDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KurulusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KurulusService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Kurulus(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.kurulus).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
