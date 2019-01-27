/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { ShopCtmUpdateComponent } from 'app/entities/shop-ctm/shop-ctm-update.component';
import { ShopCtmService } from 'app/entities/shop-ctm/shop-ctm.service';
import { ShopCtm } from 'app/shared/model/shop-ctm.model';

describe('Component Tests', () => {
    describe('ShopCtm Management Update Component', () => {
        let comp: ShopCtmUpdateComponent;
        let fixture: ComponentFixture<ShopCtmUpdateComponent>;
        let service: ShopCtmService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [ShopCtmUpdateComponent]
            })
                .overrideTemplate(ShopCtmUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ShopCtmUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShopCtmService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ShopCtm(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.shop = entity;
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
                    const entity = new ShopCtm();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.shop = entity;
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
