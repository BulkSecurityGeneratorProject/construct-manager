/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { GenericProductCategoryCtmDeleteDialogComponent } from 'app/entities/generic-product-category-ctm/generic-product-category-ctm-delete-dialog.component';
import { GenericProductCategoryCtmService } from 'app/entities/generic-product-category-ctm/generic-product-category-ctm.service';

describe('Component Tests', () => {
    describe('GenericProductCategoryCtm Management Delete Component', () => {
        let comp: GenericProductCategoryCtmDeleteDialogComponent;
        let fixture: ComponentFixture<GenericProductCategoryCtmDeleteDialogComponent>;
        let service: GenericProductCategoryCtmService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [GenericProductCategoryCtmDeleteDialogComponent]
            })
                .overrideTemplate(GenericProductCategoryCtmDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GenericProductCategoryCtmDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GenericProductCategoryCtmService);
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
