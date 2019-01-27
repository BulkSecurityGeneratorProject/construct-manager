/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { ProductCtmDeleteDialogComponent } from 'app/entities/product-ctm/product-ctm-delete-dialog.component';
import { ProductCtmService } from 'app/entities/product-ctm/product-ctm.service';

describe('Component Tests', () => {
    describe('ProductCtm Management Delete Component', () => {
        let comp: ProductCtmDeleteDialogComponent;
        let fixture: ComponentFixture<ProductCtmDeleteDialogComponent>;
        let service: ProductCtmService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [ProductCtmDeleteDialogComponent]
            })
                .overrideTemplate(ProductCtmDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductCtmDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductCtmService);
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
