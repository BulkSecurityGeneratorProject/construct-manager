/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { EstimateCtmDetailComponent } from 'app/entities/estimate-ctm/estimate-ctm-detail.component';
import { EstimateCtm } from 'app/shared/model/estimate-ctm.model';

describe('Component Tests', () => {
    describe('EstimateCtm Management Detail Component', () => {
        let comp: EstimateCtmDetailComponent;
        let fixture: ComponentFixture<EstimateCtmDetailComponent>;
        const route = ({ data: of({ estimate: new EstimateCtm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [EstimateCtmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EstimateCtmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstimateCtmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.estimate).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
