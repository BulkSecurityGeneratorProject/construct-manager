/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { ProductCtmUpdateComponent } from 'app/entities/product-ctm/product-ctm-update.component';
import { ProductCtmService } from 'app/entities/product-ctm/product-ctm.service';
import { ProductCtm } from 'app/shared/model/product-ctm.model';

describe('Component Tests', () => {
    describe('ProductCtm Management Update Component', () => {
        let comp: ProductCtmUpdateComponent;
        let fixture: ComponentFixture<ProductCtmUpdateComponent>;
        let service: ProductCtmService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [ProductCtmUpdateComponent]
            })
                .overrideTemplate(ProductCtmUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductCtmUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductCtmService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductCtm(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.product = entity;
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
                    const entity = new ProductCtm();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.product = entity;
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
