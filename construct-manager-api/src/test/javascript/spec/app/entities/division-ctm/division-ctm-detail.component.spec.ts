/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { DivisionCtmDetailComponent } from 'app/entities/division-ctm/division-ctm-detail.component';
import { DivisionCtm } from 'app/shared/model/division-ctm.model';

describe('Component Tests', () => {
    describe('DivisionCtm Management Detail Component', () => {
        let comp: DivisionCtmDetailComponent;
        let fixture: ComponentFixture<DivisionCtmDetailComponent>;
        const route = ({ data: of({ division: new DivisionCtm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [DivisionCtmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DivisionCtmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DivisionCtmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.division).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
