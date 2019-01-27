/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { RoomSizeCtmDeleteDialogComponent } from 'app/entities/room-size-ctm/room-size-ctm-delete-dialog.component';
import { RoomSizeCtmService } from 'app/entities/room-size-ctm/room-size-ctm.service';

describe('Component Tests', () => {
    describe('RoomSizeCtm Management Delete Component', () => {
        let comp: RoomSizeCtmDeleteDialogComponent;
        let fixture: ComponentFixture<RoomSizeCtmDeleteDialogComponent>;
        let service: RoomSizeCtmService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [RoomSizeCtmDeleteDialogComponent]
            })
                .overrideTemplate(RoomSizeCtmDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoomSizeCtmDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoomSizeCtmService);
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
