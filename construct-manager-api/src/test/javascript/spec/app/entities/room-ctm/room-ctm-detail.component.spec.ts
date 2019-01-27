/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { RoomCtmDetailComponent } from 'app/entities/room-ctm/room-ctm-detail.component';
import { RoomCtm } from 'app/shared/model/room-ctm.model';

describe('Component Tests', () => {
    describe('RoomCtm Management Detail Component', () => {
        let comp: RoomCtmDetailComponent;
        let fixture: ComponentFixture<RoomCtmDetailComponent>;
        const route = ({ data: of({ room: new RoomCtm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [RoomCtmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RoomCtmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoomCtmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.room).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
