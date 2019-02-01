/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OhmymagTestModule } from '../../../test.module';
import { MagasinDetailComponent } from 'app/entities/magasin/magasin-detail.component';
import { Magasin } from 'app/shared/model/magasin.model';

describe('Component Tests', () => {
    describe('Magasin Management Detail Component', () => {
        let comp: MagasinDetailComponent;
        let fixture: ComponentFixture<MagasinDetailComponent>;
        const route = ({ data: of({ magasin: new Magasin(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OhmymagTestModule],
                declarations: [MagasinDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MagasinDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MagasinDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.magasin).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
