import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoomSizeCtm } from 'app/shared/model/room-size-ctm.model';
import { RoomSizeCtmService } from './room-size-ctm.service';

@Component({
    selector: 'jhi-room-size-ctm-delete-dialog',
    templateUrl: './room-size-ctm-delete-dialog.component.html'
})
export class RoomSizeCtmDeleteDialogComponent {
    roomSize: IRoomSizeCtm;

    constructor(
        protected roomSizeService: RoomSizeCtmService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.roomSizeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'roomSizeListModification',
                content: 'Deleted an roomSize'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-room-size-ctm-delete-popup',
    template: ''
})
export class RoomSizeCtmDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roomSize }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RoomSizeCtmDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.roomSize = roomSize;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/room-size-ctm', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/room-size-ctm', { outlets: { popup: null } }]);
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
