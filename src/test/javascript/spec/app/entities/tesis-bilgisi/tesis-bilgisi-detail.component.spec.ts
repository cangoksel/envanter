/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TesisBilgisiDetailComponent } from '../../../../../../main/webapp/app/entities/tesis-bilgisi/tesis-bilgisi-detail.component';
import { TesisBilgisiService } from '../../../../../../main/webapp/app/entities/tesis-bilgisi/tesis-bilgisi.service';
import { TesisBilgisi } from '../../../../../../main/webapp/app/entities/tesis-bilgisi/tesis-bilgisi.model';

describe('Component Tests', () => {

    describe('TesisBilgisi Management Detail Component', () => {
        let comp: TesisBilgisiDetailComponent;
        let fixture: ComponentFixture<TesisBilgisiDetailComponent>;
        let service: TesisBilgisiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [TesisBilgisiDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TesisBilgisiService,
                    JhiEventManager
                ]
            }).overrideTemplate(TesisBilgisiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TesisBilgisiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TesisBilgisiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TesisBilgisi(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tesisBilgisi).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
