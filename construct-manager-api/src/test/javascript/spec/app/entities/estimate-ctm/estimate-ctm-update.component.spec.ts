/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { EstimateCtmUpdateComponent } from 'app/entities/estimate-ctm/estimate-ctm-update.component';
import { EstimateCtmService } from 'app/entities/estimate-ctm/estimate-ctm.service';
import { EstimateCtm } from 'app/shared/model/estimate-ctm.model';

describe('Component Tests', () => {
    describe('EstimateCtm Management Update Component', () => {
        let comp: EstimateCtmUpdateComponent;
        let fixture: ComponentFixture<EstimateCtmUpdateComponent>;
        let service: EstimateCtmService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [EstimateCtmUpdateComponent]
            })
                .overrideTemplate(EstimateCtmUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EstimateCtmUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstimateCtmService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EstimateCtm(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.estimate = entity;
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
                    const entity = new EstimateCtm();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.estimate = entity;
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
