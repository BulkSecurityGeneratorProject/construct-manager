/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { ShopCtmDetailComponent } from 'app/entities/shop-ctm/shop-ctm-detail.component';
import { ShopCtm } from 'app/shared/model/shop-ctm.model';

describe('Component Tests', () => {
    describe('ShopCtm Management Detail Component', () => {
        let comp: ShopCtmDetailComponent;
        let fixture: ComponentFixture<ShopCtmDetailComponent>;
        const route = ({ data: of({ shop: new ShopCtm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [ShopCtmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ShopCtmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ShopCtmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.shop).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
