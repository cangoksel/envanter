/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { KurumDetailComponent } from '../../../../../../main/webapp/app/entities/kurum/kurum-detail.component';
import { KurumService } from '../../../../../../main/webapp/app/entities/kurum/kurum.service';
import { Kurum } from '../../../../../../main/webapp/app/entities/kurum/kurum.model';

describe('Component Tests', () => {

    describe('Kurum Management Detail Component', () => {
        let comp: KurumDetailComponent;
        let fixture: ComponentFixture<KurumDetailComponent>;
        let service: KurumService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [KurumDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    KurumService,
                    JhiEventManager
                ]
            }).overrideTemplate(KurumDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KurumDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KurumService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Kurum(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.kurum).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
