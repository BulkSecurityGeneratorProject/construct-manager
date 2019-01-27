/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { RoomTypeCtmDeleteDialogComponent } from 'app/entities/room-type-ctm/room-type-ctm-delete-dialog.component';
import { RoomTypeCtmService } from 'app/entities/room-type-ctm/room-type-ctm.service';

describe('Component Tests', () => {
    describe('RoomTypeCtm Management Delete Component', () => {
        let comp: RoomTypeCtmDeleteDialogComponent;
        let fixture: ComponentFixture<RoomTypeCtmDeleteDialogComponent>;
        let service: RoomTypeCtmService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [RoomTypeCtmDeleteDialogComponent]
            })
                .overrideTemplate(RoomTypeCtmDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoomTypeCtmDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoomTypeCtmService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
