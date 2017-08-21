/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UlkeDetailComponent } from '../../../../../../main/webapp/app/entities/ulke/ulke-detail.component';
import { UlkeService } from '../../../../../../main/webapp/app/entities/ulke/ulke.service';
import { Ulke } from '../../../../../../main/webapp/app/entities/ulke/ulke.model';

describe('Component Tests', () => {

    describe('Ulke Management Detail Component', () => {
        let comp: UlkeDetailComponent;
        let fixture: ComponentFixture<UlkeDetailComponent>;
        let service: UlkeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [UlkeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UlkeService,
                    JhiEventManager
                ]
            }).overrideTemplate(UlkeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UlkeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UlkeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Ulke(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ulke).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
