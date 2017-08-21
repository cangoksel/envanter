/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MedikalTurKoduDetailComponent } from '../../../../../../main/webapp/app/entities/medikal-tur-kodu/medikal-tur-kodu-detail.component';
import { MedikalTurKoduService } from '../../../../../../main/webapp/app/entities/medikal-tur-kodu/medikal-tur-kodu.service';
import { MedikalTurKodu } from '../../../../../../main/webapp/app/entities/medikal-tur-kodu/medikal-tur-kodu.model';

describe('Component Tests', () => {

    describe('MedikalTurKodu Management Detail Component', () => {
        let comp: MedikalTurKoduDetailComponent;
        let fixture: ComponentFixture<MedikalTurKoduDetailComponent>;
        let service: MedikalTurKoduService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [MedikalTurKoduDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MedikalTurKoduService,
                    JhiEventManager
                ]
            }).overrideTemplate(MedikalTurKoduDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedikalTurKoduDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedikalTurKoduService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MedikalTurKodu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.medikalTurKodu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
