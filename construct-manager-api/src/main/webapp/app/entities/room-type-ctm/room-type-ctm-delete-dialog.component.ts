import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoomTypeCtm } from 'app/shared/model/room-type-ctm.model';
import { RoomTypeCtmService } from './room-type-ctm.service';

@Component({
    selector: 'jhi-room-type-ctm-delete-dialog',
    templateUrl: './room-type-ctm-delete-dialog.component.html'
})
export class RoomTypeCtmDeleteDialogComponent {
    roomType: IRoomTypeCtm;

    constructor(
        protected roomTypeService: RoomTypeCtmService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.roomTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'roomTypeListModification',
                content: 'Deleted an roomType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-room-type-ctm-delete-popup',
    template: ''
})
export class RoomTypeCtmDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roomType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RoomTypeCtmDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.roomType = roomType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/room-type-ctm', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/room-type-ctm', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
