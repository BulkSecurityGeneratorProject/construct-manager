import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstimateCtm } from 'app/shared/model/estimate-ctm.model';
import { EstimateCtmService } from './estimate-ctm.service';

@Component({
    selector: 'jhi-estimate-ctm-delete-dialog',
    templateUrl: './estimate-ctm-delete-dialog.component.html'
})
export class EstimateCtmDeleteDialogComponent {
    estimate: IEstimateCtm;

    constructor(
        protected estimateService: EstimateCtmService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.estimateService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'estimateListModification',
                content: 'Deleted an estimate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-estimate-ctm-delete-popup',
    template: ''
})
export class EstimateCtmDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estimate }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EstimateCtmDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.estimate = estimate;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/estimate-ctm', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/estimate-ctm', { outlets: { popup: null } }]);
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
