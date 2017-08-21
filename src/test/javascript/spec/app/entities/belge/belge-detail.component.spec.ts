/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BelgeDetailComponent } from '../../../../../../main/webapp/app/entities/belge/belge-detail.component';
import { BelgeService } from '../../../../../../main/webapp/app/entities/belge/belge.service';
import { Belge } from '../../../../../../main/webapp/app/entities/belge/belge.model';

describe('Component Tests', () => {

    describe('Belge Management Detail Component', () => {
        let comp: BelgeDetailComponent;
        let fixture: ComponentFixture<BelgeDetailComponent>;
        let service: BelgeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [BelgeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BelgeService,
                    JhiEventManager
                ]
            }).overrideTemplate(BelgeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BelgeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BelgeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Belge(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.belge).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
