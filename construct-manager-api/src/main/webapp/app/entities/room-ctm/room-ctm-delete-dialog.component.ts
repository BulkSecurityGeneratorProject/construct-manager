import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoomCtm } from 'app/shared/model/room-ctm.model';
import { RoomCtmService } from './room-ctm.service';

@Component({
    selector: 'jhi-room-ctm-delete-dialog',
    templateUrl: './room-ctm-delete-dialog.component.html'
})
export class RoomCtmDeleteDialogComponent {
    room: IRoomCtm;

    constructor(protected roomService: RoomCtmService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.roomService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'roomListModification',
                content: 'Deleted an room'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-room-ctm-delete-popup',
    template: ''
})
export class RoomCtmDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ room }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RoomCtmDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.room = room;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/room-ctm', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/room-ctm', { outlets: { popup: null } }]);
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
