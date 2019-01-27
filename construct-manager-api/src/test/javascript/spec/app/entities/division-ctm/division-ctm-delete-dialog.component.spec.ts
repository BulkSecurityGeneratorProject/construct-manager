/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { DivisionCtmDeleteDialogComponent } from 'app/entities/division-ctm/division-ctm-delete-dialog.component';
import { DivisionCtmService } from 'app/entities/division-ctm/division-ctm.service';

describe('Component Tests', () => {
    describe('DivisionCtm Management Delete Component', () => {
        let comp: DivisionCtmDeleteDialogComponent;
        let fixture: ComponentFixture<DivisionCtmDeleteDialogComponent>;
        let service: DivisionCtmService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [DivisionCtmDeleteDialogComponent]
            })
                .overrideTemplate(DivisionCtmDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DivisionCtmDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DivisionCtmService);
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
