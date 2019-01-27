/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { GenericProductCtmDeleteDialogComponent } from 'app/entities/generic-product-ctm/generic-product-ctm-delete-dialog.component';
import { GenericProductCtmService } from 'app/entities/generic-product-ctm/generic-product-ctm.service';

describe('Component Tests', () => {
    describe('GenericProductCtm Management Delete Component', () => {
        let comp: GenericProductCtmDeleteDialogComponent;
        let fixture: ComponentFixture<GenericProductCtmDeleteDialogComponent>;
        let service: GenericProductCtmService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [GenericProductCtmDeleteDialogComponent]
            })
                .overrideTemplate(GenericProductCtmDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GenericProductCtmDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GenericProductCtmService);
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
