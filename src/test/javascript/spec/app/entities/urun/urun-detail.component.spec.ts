/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UrunDetailComponent } from '../../../../../../main/webapp/app/entities/urun/urun-detail.component';
import { UrunService } from '../../../../../../main/webapp/app/entities/urun/urun.service';
import { Urun } from '../../../../../../main/webapp/app/entities/urun/urun.model';

describe('Component Tests', () => {

    describe('Urun Management Detail Component', () => {
        let comp: UrunDetailComponent;
        let fixture: ComponentFixture<UrunDetailComponent>;
        let service: UrunService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [UrunDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UrunService,
                    JhiEventManager
                ]
            }).overrideTemplate(UrunDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UrunDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UrunService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Urun(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.urun).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
