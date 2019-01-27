/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { EstimateProductCtmUpdateComponent } from 'app/entities/estimate-product-ctm/estimate-product-ctm-update.component';
import { EstimateProductCtmService } from 'app/entities/estimate-product-ctm/estimate-product-ctm.service';
import { EstimateProductCtm } from 'app/shared/model/estimate-product-ctm.model';

describe('Component Tests', () => {
    describe('EstimateProductCtm Management Update Component', () => {
        let comp: EstimateProductCtmUpdateComponent;
        let fixture: ComponentFixture<EstimateProductCtmUpdateComponent>;
        let service: EstimateProductCtmService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [EstimateProductCtmUpdateComponent]
            })
                .overrideTemplate(EstimateProductCtmUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EstimateProductCtmUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstimateProductCtmService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EstimateProductCtm(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.estimateProduct = entity;
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
                    const entity = new EstimateProductCtm();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.estimateProduct = entity;
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
