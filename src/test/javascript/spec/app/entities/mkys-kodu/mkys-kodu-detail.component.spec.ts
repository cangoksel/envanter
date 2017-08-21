/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MkysKoduDetailComponent } from '../../../../../../main/webapp/app/entities/mkys-kodu/mkys-kodu-detail.component';
import { MkysKoduService } from '../../../../../../main/webapp/app/entities/mkys-kodu/mkys-kodu.service';
import { MkysKodu } from '../../../../../../main/webapp/app/entities/mkys-kodu/mkys-kodu.model';

describe('Component Tests', () => {

    describe('MkysKodu Management Detail Component', () => {
        let comp: MkysKoduDetailComponent;
        let fixture: ComponentFixture<MkysKoduDetailComponent>;
        let service: MkysKoduService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [MkysKoduDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MkysKoduService,
                    JhiEventManager
                ]
            }).overrideTemplate(MkysKoduDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MkysKoduDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MkysKoduService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MkysKodu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mkysKodu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
