import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHomeCtm } from 'app/shared/model/home-ctm.model';
import { HomeCtmService } from './home-ctm.service';

@Component({
    selector: 'jhi-home-ctm-delete-dialog',
    templateUrl: './home-ctm-delete-dialog.component.html'
})
export class HomeCtmDeleteDialogComponent {
    home: IHomeCtm;

    constructor(protected homeService: HomeCtmService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.homeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'homeListModification',
                content: 'Deleted an home'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-home-ctm-delete-popup',
    template: ''
})
export class HomeCtmDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ home }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HomeCtmDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.home = home;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/home-ctm', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/home-ctm', { outlets: { popup: null } }]);
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
