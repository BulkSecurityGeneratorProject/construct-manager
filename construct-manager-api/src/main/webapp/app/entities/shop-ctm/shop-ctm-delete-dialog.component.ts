import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShopCtm } from 'app/shared/model/shop-ctm.model';
import { ShopCtmService } from './shop-ctm.service';

@Component({
    selector: 'jhi-shop-ctm-delete-dialog',
    templateUrl: './shop-ctm-delete-dialog.component.html'
})
export class ShopCtmDeleteDialogComponent {
    shop: IShopCtm;

    constructor(protected shopService: ShopCtmService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shopService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'shopListModification',
                content: 'Deleted an shop'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shop-ctm-delete-popup',
    template: ''
})
export class ShopCtmDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ shop }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ShopCtmDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.shop = shop;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/shop-ctm', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/shop-ctm', { outlets: { popup: null } }]);
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
