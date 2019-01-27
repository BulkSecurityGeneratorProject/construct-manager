/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { RoomSizeCtmUpdateComponent } from 'app/entities/room-size-ctm/room-size-ctm-update.component';
import { RoomSizeCtmService } from 'app/entities/room-size-ctm/room-size-ctm.service';
import { RoomSizeCtm } from 'app/shared/model/room-size-ctm.model';

describe('Component Tests', () => {
    describe('RoomSizeCtm Management Update Component', () => {
        let comp: RoomSizeCtmUpdateComponent;
        let fixture: ComponentFixture<RoomSizeCtmUpdateComponent>;
        let service: RoomSizeCtmService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [RoomSizeCtmUpdateComponent]
            })
                .overrideTemplate(RoomSizeCtmUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RoomSizeCtmUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoomSizeCtmService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RoomSizeCtm(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.roomSize = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RoomSizeCtm();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.roomSize = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
