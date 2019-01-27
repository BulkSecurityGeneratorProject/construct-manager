import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstimateProductCtm } from 'app/shared/model/estimate-product-ctm.model';
import { EstimateProductCtmService } from './estimate-product-ctm.service';

@Component({
    selector: 'jhi-estimate-product-ctm-delete-dialog',
    templateUrl: './estimate-product-ctm-delete-dialog.component.html'
})
export class EstimateProductCtmDeleteDialogComponent {
    estimateProduct: IEstimateProductCtm;

    constructor(
        protected estimateProductService: EstimateProductCtmService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.estimateProductService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'estimateProductListModification',
                content: 'Deleted an estimateProduct'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-estimate-product-ctm-delete-popup',
    template: ''
})
export class EstimateProductCtmDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estimateProduct }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EstimateProductCtmDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.estimateProduct = estimateProduct;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/estimate-product-ctm', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/estimate-product-ctm', { outlets: { popup: null } }]);
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
