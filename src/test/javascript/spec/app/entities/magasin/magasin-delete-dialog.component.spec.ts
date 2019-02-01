/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OhmymagTestModule } from '../../../test.module';
import { MagasinDeleteDialogComponent } from 'app/entities/magasin/magasin-delete-dialog.component';
import { MagasinService } from 'app/entities/magasin/magasin.service';

describe('Component Tests', () => {
    describe('Magasin Management Delete Component', () => {
        let comp: MagasinDeleteDialogComponent;
        let fixture: ComponentFixture<MagasinDeleteDialogComponent>;
        let service: MagasinService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OhmymagTestModule],
                declarations: [MagasinDeleteDialogComponent]
            })
                .overrideTemplate(MagasinDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MagasinDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MagasinService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
