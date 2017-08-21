/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AdresDetailComponent } from '../../../../../../main/webapp/app/entities/adres/adres-detail.component';
import { AdresService } from '../../../../../../main/webapp/app/entities/adres/adres.service';
import { Adres } from '../../../../../../main/webapp/app/entities/adres/adres.model';

describe('Component Tests', () => {

    describe('Adres Management Detail Component', () => {
        let comp: AdresDetailComponent;
        let fixture: ComponentFixture<AdresDetailComponent>;
        let service: AdresService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [AdresDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AdresService,
                    JhiEventManager
                ]
            }).overrideTemplate(AdresDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdresDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdresService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Adres(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.adres).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
