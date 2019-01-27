import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGenericProductCategoryCtm } from 'app/shared/model/generic-product-category-ctm.model';
import { GenericProductCategoryCtmService } from './generic-product-category-ctm.service';

@Component({
    selector: 'jhi-generic-product-category-ctm-delete-dialog',
    templateUrl: './generic-product-category-ctm-delete-dialog.component.html'
})
export class GenericProductCategoryCtmDeleteDialogComponent {
    genericProductCategory: IGenericProductCategoryCtm;

    constructor(
        protected genericProductCategoryService: GenericProductCategoryCtmService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.genericProductCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'genericProductCategoryListModification',
                content: 'Deleted an genericProductCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-generic-product-category-ctm-delete-popup',
    template: ''
})
export class GenericProductCategoryCtmDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ genericProductCategory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GenericProductCategoryCtmDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.genericProductCategory = genericProductCategory;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/generic-product-category-ctm', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/generic-product-category-ctm', { outlets: { popup: null } }]);
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
