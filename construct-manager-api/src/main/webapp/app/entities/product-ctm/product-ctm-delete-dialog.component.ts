import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductCtm } from 'app/shared/model/product-ctm.model';
import { ProductCtmService } from './product-ctm.service';

@Component({
    selector: 'jhi-product-ctm-delete-dialog',
    templateUrl: './product-ctm-delete-dialog.component.html'
})
export class ProductCtmDeleteDialogComponent {
    product: IProductCtm;

    constructor(protected productService: ProductCtmService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productListModification',
                content: 'Deleted an product'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-ctm-delete-popup',
    template: ''
})
export class ProductCtmDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ product }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductCtmDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.product = product;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/product-ctm', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/product-ctm', { outlets: { popup: null } }]);
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
