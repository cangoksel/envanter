/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InventoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrtaklikBilgileriDetailComponent } from '../../../../../../main/webapp/app/entities/ortaklik-bilgileri/ortaklik-bilgileri-detail.component';
import { OrtaklikBilgileriService } from '../../../../../../main/webapp/app/entities/ortaklik-bilgileri/ortaklik-bilgileri.service';
import { OrtaklikBilgileri } from '../../../../../../main/webapp/app/entities/ortaklik-bilgileri/ortaklik-bilgileri.model';

describe('Component Tests', () => {

    describe('OrtaklikBilgileri Management Detail Component', () => {
        let comp: OrtaklikBilgileriDetailComponent;
        let fixture: ComponentFixture<OrtaklikBilgileriDetailComponent>;
        let service: OrtaklikBilgileriService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InventoryTestModule],
                declarations: [OrtaklikBilgileriDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrtaklikBilgileriService,
                    JhiEventManager
                ]
            }).overrideTemplate(OrtaklikBilgileriDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrtaklikBilgileriDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrtaklikBilgileriService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrtaklikBilgileri(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ortaklikBilgileri).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
