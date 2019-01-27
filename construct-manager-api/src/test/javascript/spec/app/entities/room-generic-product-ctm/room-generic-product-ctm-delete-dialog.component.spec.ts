/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { RoomGenericProductCtmDeleteDialogComponent } from 'app/entities/room-generic-product-ctm/room-generic-product-ctm-delete-dialog.component';
import { RoomGenericProductCtmService } from 'app/entities/room-generic-product-ctm/room-generic-product-ctm.service';

describe('Component Tests', () => {
    describe('RoomGenericProductCtm Management Delete Component', () => {
        let comp: RoomGenericProductCtmDeleteDialogComponent;
        let fixture: ComponentFixture<RoomGenericProductCtmDeleteDialogComponent>;
        let service: RoomGenericProductCtmService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [RoomGenericProductCtmDeleteDialogComponent]
            })
                .overrideTemplate(RoomGenericProductCtmDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoomGenericProductCtmDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoomGenericProductCtmService);
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
