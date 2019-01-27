/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { EstimateCtmDeleteDialogComponent } from 'app/entities/estimate-ctm/estimate-ctm-delete-dialog.component';
import { EstimateCtmService } from 'app/entities/estimate-ctm/estimate-ctm.service';

describe('Component Tests', () => {
    describe('EstimateCtm Management Delete Component', () => {
        let comp: EstimateCtmDeleteDialogComponent;
        let fixture: ComponentFixture<EstimateCtmDeleteDialogComponent>;
        let service: EstimateCtmService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [EstimateCtmDeleteDialogComponent]
            })
                .overrideTemplate(EstimateCtmDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstimateCtmDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstimateCtmService);
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
