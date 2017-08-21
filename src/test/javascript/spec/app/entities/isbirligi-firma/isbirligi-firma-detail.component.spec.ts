/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { IsbirligiFirmaDetailComponent } from '../../../../../../main/webapp/app/entities/isbirligi-firma/isbirligi-firma-detail.component';
import { IsbirligiFirmaService } from '../../../../../../main/webapp/app/entities/isbirligi-firma/isbirligi-firma.service';
import { IsbirligiFirma } from '../../../../../../main/webapp/app/entities/isbirligi-firma/isbirligi-firma.model';

describe('Component Tests', () => {

    describe('IsbirligiFirma Management Detail Component', () => {
        let comp: IsbirligiFirmaDetailComponent;
        let fixture: ComponentFixture<IsbirligiFirmaDetailComponent>;
        let service: IsbirligiFirmaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [IsbirligiFirmaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    IsbirligiFirmaService,
                    JhiEventManager
                ]
            }).overrideTemplate(IsbirligiFirmaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IsbirligiFirmaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IsbirligiFirmaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new IsbirligiFirma(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.isbirligiFirma).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
