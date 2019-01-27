/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { HomeCtmUpdateComponent } from 'app/entities/home-ctm/home-ctm-update.component';
import { HomeCtmService } from 'app/entities/home-ctm/home-ctm.service';
import { HomeCtm } from 'app/shared/model/home-ctm.model';

describe('Component Tests', () => {
    describe('HomeCtm Management Update Component', () => {
        let comp: HomeCtmUpdateComponent;
        let fixture: ComponentFixture<HomeCtmUpdateComponent>;
        let service: HomeCtmService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [HomeCtmUpdateComponent]
            })
                .overrideTemplate(HomeCtmUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HomeCtmUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HomeCtmService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new HomeCtm(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.home = entity;
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
                    const entity = new HomeCtm();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.home = entity;
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
