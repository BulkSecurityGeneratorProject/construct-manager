/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { RoomCtmUpdateComponent } from 'app/entities/room-ctm/room-ctm-update.component';
import { RoomCtmService } from 'app/entities/room-ctm/room-ctm.service';
import { RoomCtm } from 'app/shared/model/room-ctm.model';

describe('Component Tests', () => {
    describe('RoomCtm Management Update Component', () => {
        let comp: RoomCtmUpdateComponent;
        let fixture: ComponentFixture<RoomCtmUpdateComponent>;
        let service: RoomCtmService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [RoomCtmUpdateComponent]
            })
                .overrideTemplate(RoomCtmUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RoomCtmUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoomCtmService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RoomCtm(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.room = entity;
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
                    const entity = new RoomCtm();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.room = entity;
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
