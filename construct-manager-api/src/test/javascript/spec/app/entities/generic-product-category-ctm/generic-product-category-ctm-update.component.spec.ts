/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { GenericProductCategoryCtmUpdateComponent } from 'app/entities/generic-product-category-ctm/generic-product-category-ctm-update.component';
import { GenericProductCategoryCtmService } from 'app/entities/generic-product-category-ctm/generic-product-category-ctm.service';
import { GenericProductCategoryCtm } from 'app/shared/model/generic-product-category-ctm.model';

describe('Component Tests', () => {
    describe('GenericProductCategoryCtm Management Update Component', () => {
        let comp: GenericProductCategoryCtmUpdateComponent;
        let fixture: ComponentFixture<GenericProductCategoryCtmUpdateComponent>;
        let service: GenericProductCategoryCtmService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [GenericProductCategoryCtmUpdateComponent]
            })
                .overrideTemplate(GenericProductCategoryCtmUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GenericProductCategoryCtmUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GenericProductCategoryCtmService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GenericProductCategoryCtm(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.genericProductCategory = entity;
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
                    const entity = new GenericProductCategoryCtm();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.genericProductCategory = entity;
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
