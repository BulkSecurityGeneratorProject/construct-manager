/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { RoomTypeCtmDetailComponent } from 'app/entities/room-type-ctm/room-type-ctm-detail.component';
import { RoomTypeCtm } from 'app/shared/model/room-type-ctm.model';

describe('Component Tests', () => {
    describe('RoomTypeCtm Management Detail Component', () => {
        let comp: RoomTypeCtmDetailComponent;
        let fixture: ComponentFixture<RoomTypeCtmDetailComponent>;
        const route = ({ data: of({ roomType: new RoomTypeCtm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [RoomTypeCtmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RoomTypeCtmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoomTypeCtmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.roomType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
