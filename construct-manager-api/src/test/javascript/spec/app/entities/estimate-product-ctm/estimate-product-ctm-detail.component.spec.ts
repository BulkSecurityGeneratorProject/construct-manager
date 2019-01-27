/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { EstimateProductCtmDetailComponent } from 'app/entities/estimate-product-ctm/estimate-product-ctm-detail.component';
import { EstimateProductCtm } from 'app/shared/model/estimate-product-ctm.model';

describe('Component Tests', () => {
    describe('EstimateProductCtm Management Detail Component', () => {
        let comp: EstimateProductCtmDetailComponent;
        let fixture: ComponentFixture<EstimateProductCtmDetailComponent>;
        const route = ({ data: of({ estimateProduct: new EstimateProductCtm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [EstimateProductCtmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EstimateProductCtmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstimateProductCtmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.estimateProduct).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
