/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { HomeCtmDetailComponent } from 'app/entities/home-ctm/home-ctm-detail.component';
import { HomeCtm } from 'app/shared/model/home-ctm.model';

describe('Component Tests', () => {
    describe('HomeCtm Management Detail Component', () => {
        let comp: HomeCtmDetailComponent;
        let fixture: ComponentFixture<HomeCtmDetailComponent>;
        const route = ({ data: of({ home: new HomeCtm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [HomeCtmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HomeCtmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HomeCtmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.home).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
