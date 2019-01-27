/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { GenericProductCtmUpdateComponent } from 'app/entities/generic-product-ctm/generic-product-ctm-update.component';
import { GenericProductCtmService } from 'app/entities/generic-product-ctm/generic-product-ctm.service';
import { GenericProductCtm } from 'app/shared/model/generic-product-ctm.model';

describe('Component Tests', () => {
    describe('GenericProductCtm Management Update Component', () => {
        let comp: GenericProductCtmUpdateComponent;
        let fixture: ComponentFixture<GenericProductCtmUpdateComponent>;
        let service: GenericProductCtmService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [GenericProductCtmUpdateComponent]
            })
                .overrideTemplate(GenericProductCtmUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GenericProductCtmUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GenericProductCtmService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GenericProductCtm(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.genericProduct = entity;
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
                    const entity = new GenericProductCtm();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.genericProduct = entity;
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
