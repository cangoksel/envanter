/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProjeBilgisiDetailComponent } from '../../../../../../main/webapp/app/entities/proje-bilgisi/proje-bilgisi-detail.component';
import { ProjeBilgisiService } from '../../../../../../main/webapp/app/entities/proje-bilgisi/proje-bilgisi.service';
import { ProjeBilgisi } from '../../../../../../main/webapp/app/entities/proje-bilgisi/proje-bilgisi.model';

describe('Component Tests', () => {

    describe('ProjeBilgisi Management Detail Component', () => {
        let comp: ProjeBilgisiDetailComponent;
        let fixture: ComponentFixture<ProjeBilgisiDetailComponent>;
        let service: ProjeBilgisiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [ProjeBilgisiDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProjeBilgisiService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProjeBilgisiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjeBilgisiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjeBilgisiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProjeBilgisi(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.projeBilgisi).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
