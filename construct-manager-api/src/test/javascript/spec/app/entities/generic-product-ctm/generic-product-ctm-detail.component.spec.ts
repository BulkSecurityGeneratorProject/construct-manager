/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { GenericProductCtmDetailComponent } from 'app/entities/generic-product-ctm/generic-product-ctm-detail.component';
import { GenericProductCtm } from 'app/shared/model/generic-product-ctm.model';

describe('Component Tests', () => {
    describe('GenericProductCtm Management Detail Component', () => {
        let comp: GenericProductCtmDetailComponent;
        let fixture: ComponentFixture<GenericProductCtmDetailComponent>;
        const route = ({ data: of({ genericProduct: new GenericProductCtm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [GenericProductCtmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GenericProductCtmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GenericProductCtmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.genericProduct).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
