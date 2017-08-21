/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BolgeDetailComponent } from '../../../../../../main/webapp/app/entities/bolge/bolge-detail.component';
import { BolgeService } from '../../../../../../main/webapp/app/entities/bolge/bolge.service';
import { Bolge } from '../../../../../../main/webapp/app/entities/bolge/bolge.model';

describe('Component Tests', () => {

    describe('Bolge Management Detail Component', () => {
        let comp: BolgeDetailComponent;
        let fixture: ComponentFixture<BolgeDetailComponent>;
        let service: BolgeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [BolgeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BolgeService,
                    JhiEventManager
                ]
            }).overrideTemplate(BolgeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BolgeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BolgeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Bolge(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bolge).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
