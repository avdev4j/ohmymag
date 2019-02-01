import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMagasin } from 'app/shared/model/magasin.model';
import { MagasinService } from './magasin.service';

@Component({
    selector: 'jhi-magasin-delete-dialog',
    templateUrl: './magasin-delete-dialog.component.html'
})
export class MagasinDeleteDialogComponent {
    magasin: IMagasin;

    constructor(protected magasinService: MagasinService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.magasinService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'magasinListModification',
                content: 'Deleted an magasin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-magasin-delete-popup',
    template: ''
})
export class MagasinDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ magasin }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MagasinDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.magasin = magasin;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/magasin', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/magasin', { outlets: { popup: null } }]);
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
