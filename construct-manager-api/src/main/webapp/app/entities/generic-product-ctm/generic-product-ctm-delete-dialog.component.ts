import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGenericProductCtm } from 'app/shared/model/generic-product-ctm.model';
import { GenericProductCtmService } from './generic-product-ctm.service';

@Component({
    selector: 'jhi-generic-product-ctm-delete-dialog',
    templateUrl: './generic-product-ctm-delete-dialog.component.html'
})
export class GenericProductCtmDeleteDialogComponent {
    genericProduct: IGenericProductCtm;

    constructor(
        protected genericProductService: GenericProductCtmService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.genericProductService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'genericProductListModification',
                content: 'Deleted an genericProduct'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-generic-product-ctm-delete-popup',
    template: ''
})
export class GenericProductCtmDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ genericProduct }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GenericProductCtmDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.genericProduct = genericProduct;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/generic-product-ctm', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/generic-product-ctm', { outlets: { popup: null } }]);
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
