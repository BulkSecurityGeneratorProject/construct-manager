/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { ShopCtmDeleteDialogComponent } from 'app/entities/shop-ctm/shop-ctm-delete-dialog.component';
import { ShopCtmService } from 'app/entities/shop-ctm/shop-ctm.service';

describe('Component Tests', () => {
    describe('ShopCtm Management Delete Component', () => {
        let comp: ShopCtmDeleteDialogComponent;
        let fixture: ComponentFixture<ShopCtmDeleteDialogComponent>;
        let service: ShopCtmService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [ShopCtmDeleteDialogComponent]
            })
                .overrideTemplate(ShopCtmDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ShopCtmDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShopCtmService);
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
