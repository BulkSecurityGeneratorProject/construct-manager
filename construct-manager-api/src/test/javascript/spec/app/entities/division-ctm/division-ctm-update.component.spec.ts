/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { DivisionCtmUpdateComponent } from 'app/entities/division-ctm/division-ctm-update.component';
import { DivisionCtmService } from 'app/entities/division-ctm/division-ctm.service';
import { DivisionCtm } from 'app/shared/model/division-ctm.model';

describe('Component Tests', () => {
    describe('DivisionCtm Management Update Component', () => {
        let comp: DivisionCtmUpdateComponent;
        let fixture: ComponentFixture<DivisionCtmUpdateComponent>;
        let service: DivisionCtmService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [DivisionCtmUpdateComponent]
            })
                .overrideTemplate(DivisionCtmUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DivisionCtmUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DivisionCtmService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DivisionCtm(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.division = entity;
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
                    const entity = new DivisionCtm();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.division = entity;
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
