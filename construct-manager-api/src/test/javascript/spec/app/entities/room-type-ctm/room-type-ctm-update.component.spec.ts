/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { RoomTypeCtmUpdateComponent } from 'app/entities/room-type-ctm/room-type-ctm-update.component';
import { RoomTypeCtmService } from 'app/entities/room-type-ctm/room-type-ctm.service';
import { RoomTypeCtm } from 'app/shared/model/room-type-ctm.model';

describe('Component Tests', () => {
    describe('RoomTypeCtm Management Update Component', () => {
        let comp: RoomTypeCtmUpdateComponent;
        let fixture: ComponentFixture<RoomTypeCtmUpdateComponent>;
        let service: RoomTypeCtmService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [RoomTypeCtmUpdateComponent]
            })
                .overrideTemplate(RoomTypeCtmUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RoomTypeCtmUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoomTypeCtmService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RoomTypeCtm(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.roomType = entity;
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
                    const entity = new RoomTypeCtm();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.roomType = entity;
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
