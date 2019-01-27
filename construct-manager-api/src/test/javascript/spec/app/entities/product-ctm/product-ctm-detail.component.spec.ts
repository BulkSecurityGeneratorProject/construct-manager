/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { ProductCtmDetailComponent } from 'app/entities/product-ctm/product-ctm-detail.component';
import { ProductCtm } from 'app/shared/model/product-ctm.model';

describe('Component Tests', () => {
    describe('ProductCtm Management Detail Component', () => {
        let comp: ProductCtmDetailComponent;
        let fixture: ComponentFixture<ProductCtmDetailComponent>;
        const route = ({ data: of({ product: new ProductCtm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [ProductCtmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductCtmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductCtmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.product).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
