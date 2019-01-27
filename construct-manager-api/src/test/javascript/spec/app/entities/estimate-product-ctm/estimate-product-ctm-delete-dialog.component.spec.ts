/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { EstimateProductCtmDeleteDialogComponent } from 'app/entities/estimate-product-ctm/estimate-product-ctm-delete-dialog.component';
import { EstimateProductCtmService } from 'app/entities/estimate-product-ctm/estimate-product-ctm.service';

describe('Component Tests', () => {
    describe('EstimateProductCtm Management Delete Component', () => {
        let comp: EstimateProductCtmDeleteDialogComponent;
        let fixture: ComponentFixture<EstimateProductCtmDeleteDialogComponent>;
        let service: EstimateProductCtmService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [EstimateProductCtmDeleteDialogComponent]
            })
                .overrideTemplate(EstimateProductCtmDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstimateProductCtmDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstimateProductCtmService);
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
