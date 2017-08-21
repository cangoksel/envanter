/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UrunGrubuDetailComponent } from '../../../../../../main/webapp/app/entities/urun-grubu/urun-grubu-detail.component';
import { UrunGrubuService } from '../../../../../../main/webapp/app/entities/urun-grubu/urun-grubu.service';
import { UrunGrubu } from '../../../../../../main/webapp/app/entities/urun-grubu/urun-grubu.model';

describe('Component Tests', () => {

    describe('UrunGrubu Management Detail Component', () => {
        let comp: UrunGrubuDetailComponent;
        let fixture: ComponentFixture<UrunGrubuDetailComponent>;
        let service: UrunGrubuService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [UrunGrubuDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UrunGrubuService,
                    JhiEventManager
                ]
            }).overrideTemplate(UrunGrubuDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UrunGrubuDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UrunGrubuService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UrunGrubu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.urunGrubu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
