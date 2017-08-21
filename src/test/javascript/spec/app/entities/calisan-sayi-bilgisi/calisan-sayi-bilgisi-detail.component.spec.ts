/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CalisanSayiBilgisiDetailComponent } from '../../../../../../main/webapp/app/entities/calisan-sayi-bilgisi/calisan-sayi-bilgisi-detail.component';
import { CalisanSayiBilgisiService } from '../../../../../../main/webapp/app/entities/calisan-sayi-bilgisi/calisan-sayi-bilgisi.service';
import { CalisanSayiBilgisi } from '../../../../../../main/webapp/app/entities/calisan-sayi-bilgisi/calisan-sayi-bilgisi.model';

describe('Component Tests', () => {

    describe('CalisanSayiBilgisi Management Detail Component', () => {
        let comp: CalisanSayiBilgisiDetailComponent;
        let fixture: ComponentFixture<CalisanSayiBilgisiDetailComponent>;
        let service: CalisanSayiBilgisiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [CalisanSayiBilgisiDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CalisanSayiBilgisiService,
                    JhiEventManager
                ]
            }).overrideTemplate(CalisanSayiBilgisiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CalisanSayiBilgisiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CalisanSayiBilgisiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CalisanSayiBilgisi(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.calisanSayiBilgisi).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
