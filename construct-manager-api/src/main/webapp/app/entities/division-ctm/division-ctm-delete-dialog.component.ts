import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDivisionCtm } from 'app/shared/model/division-ctm.model';
import { DivisionCtmService } from './division-ctm.service';

@Component({
    selector: 'jhi-division-ctm-delete-dialog',
    templateUrl: './division-ctm-delete-dialog.component.html'
})
export class DivisionCtmDeleteDialogComponent {
    division: IDivisionCtm;

    constructor(
        protected divisionService: DivisionCtmService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.divisionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'divisionListModification',
                content: 'Deleted an division'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-division-ctm-delete-popup',
    template: ''
})
export class DivisionCtmDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ division }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DivisionCtmDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.division = division;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/division-ctm', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/division-ctm', { outlets: { popup: null } }]);
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
