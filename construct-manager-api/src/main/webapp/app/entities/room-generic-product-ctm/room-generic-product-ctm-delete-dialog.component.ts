import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoomGenericProductCtm } from 'app/shared/model/room-generic-product-ctm.model';
import { RoomGenericProductCtmService } from './room-generic-product-ctm.service';

@Component({
    selector: 'jhi-room-generic-product-ctm-delete-dialog',
    templateUrl: './room-generic-product-ctm-delete-dialog.component.html'
})
export class RoomGenericProductCtmDeleteDialogComponent {
    roomGenericProduct: IRoomGenericProductCtm;

    constructor(
        protected roomGenericProductService: RoomGenericProductCtmService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.roomGenericProductService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'roomGenericProductListModification',
                content: 'Deleted an roomGenericProduct'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-room-generic-product-ctm-delete-popup',
    template: ''
})
export class RoomGenericProductCtmDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roomGenericProduct }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RoomGenericProductCtmDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.roomGenericProduct = roomGenericProduct;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/room-generic-product-ctm', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/room-generic-product-ctm', { outlets: { popup: null } }]);
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
