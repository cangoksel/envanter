/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { IsyeriBilgileriDetailComponent } from '../../../../../../main/webapp/app/entities/isyeri-bilgileri/isyeri-bilgileri-detail.component';
import { IsyeriBilgileriService } from '../../../../../../main/webapp/app/entities/isyeri-bilgileri/isyeri-bilgileri.service';
import { IsyeriBilgileri } from '../../../../../../main/webapp/app/entities/isyeri-bilgileri/isyeri-bilgileri.model';

describe('Component Tests', () => {

    describe('IsyeriBilgileri Management Detail Component', () => {
        let comp: IsyeriBilgileriDetailComponent;
        let fixture: ComponentFixture<IsyeriBilgileriDetailComponent>;
        let service: IsyeriBilgileriService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [IsyeriBilgileriDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    IsyeriBilgileriService,
                    JhiEventManager
                ]
            }).overrideTemplate(IsyeriBilgileriDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IsyeriBilgileriDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IsyeriBilgileriService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new IsyeriBilgileri(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.isyeriBilgileri).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
