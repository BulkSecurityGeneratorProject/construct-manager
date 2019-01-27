/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { GenericProductCategoryCtmDetailComponent } from 'app/entities/generic-product-category-ctm/generic-product-category-ctm-detail.component';
import { GenericProductCategoryCtm } from 'app/shared/model/generic-product-category-ctm.model';

describe('Component Tests', () => {
    describe('GenericProductCategoryCtm Management Detail Component', () => {
        let comp: GenericProductCategoryCtmDetailComponent;
        let fixture: ComponentFixture<GenericProductCategoryCtmDetailComponent>;
        const route = ({ data: of({ genericProductCategory: new GenericProductCategoryCtm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [GenericProductCategoryCtmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GenericProductCategoryCtmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GenericProductCategoryCtmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.genericProductCategory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
