/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConstructManagerApiTestModule } from '../../../test.module';
import { HomeCtmDeleteDialogComponent } from 'app/entities/home-ctm/home-ctm-delete-dialog.component';
import { HomeCtmService } from 'app/entities/home-ctm/home-ctm.service';

describe('Component Tests', () => {
    describe('HomeCtm Management Delete Component', () => {
        let comp: HomeCtmDeleteDialogComponent;
        let fixture: ComponentFixture<HomeCtmDeleteDialogComponent>;
        let service: HomeCtmService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ConstructManagerApiTestModule],
                declarations: [HomeCtmDeleteDialogComponent]
            })
                .overrideTemplate(HomeCtmDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HomeCtmDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HomeCtmService);
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
