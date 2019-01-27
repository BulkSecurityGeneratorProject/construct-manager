/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { RoomGenericProductCtmDetailComponent } from 'app/entities/room-generic-product-ctm/room-generic-product-ctm-detail.component';
import { RoomGenericProductCtm } from 'app/shared/model/room-generic-product-ctm.model';

describe('Component Tests', () => {
    describe('RoomGenericProductCtm Management Detail Component', () => {
        let comp: RoomGenericProductCtmDetailComponent;
        let fixture: ComponentFixture<RoomGenericProductCtmDetailComponent>;
        const route = ({ data: of({ roomGenericProduct: new RoomGenericProductCtm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [RoomGenericProductCtmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RoomGenericProductCtmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoomGenericProductCtmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.roomGenericProduct).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
