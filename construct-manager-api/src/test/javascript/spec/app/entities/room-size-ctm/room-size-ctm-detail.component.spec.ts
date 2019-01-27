/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { RoomSizeCtmDetailComponent } from 'app/entities/room-size-ctm/room-size-ctm-detail.component';
import { RoomSizeCtm } from 'app/shared/model/room-size-ctm.model';

describe('Component Tests', () => {
    describe('RoomSizeCtm Management Detail Component', () => {
        let comp: RoomSizeCtmDetailComponent;
        let fixture: ComponentFixture<RoomSizeCtmDetailComponent>;
        const route = ({ data: of({ roomSize: new RoomSizeCtm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [RoomSizeCtmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RoomSizeCtmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoomSizeCtmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.roomSize).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
