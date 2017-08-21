/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { KisiDetailComponent } from '../../../../../../main/webapp/app/entities/kisi/kisi-detail.component';
import { KisiService } from '../../../../../../main/webapp/app/entities/kisi/kisi.service';
import { Kisi } from '../../../../../../main/webapp/app/entities/kisi/kisi.model';

describe('Component Tests', () => {

    describe('Kisi Management Detail Component', () => {
        let comp: KisiDetailComponent;
        let fixture: ComponentFixture<KisiDetailComponent>;
        let service: KisiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [KisiDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    KisiService,
                    JhiEventManager
                ]
            }).overrideTemplate(KisiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KisiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KisiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Kisi(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.kisi).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
