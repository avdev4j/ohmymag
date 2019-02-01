import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OhmymagSharedModule } from 'app/shared';
import {
    MagasinComponent,
    MagasinDetailComponent,
    MagasinUpdateComponent,
    MagasinDeletePopupComponent,
    MagasinDeleteDialogComponent,
    magasinRoute,
    magasinPopupRoute
} from './';

const ENTITY_STATES = [...magasinRoute, ...magasinPopupRoute];

@NgModule({
    imports: [OhmymagSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MagasinComponent,
        MagasinDetailComponent,
        MagasinUpdateComponent,
        MagasinDeleteDialogComponent,
        MagasinDeletePopupComponent
    ],
    entryComponents: [MagasinComponent, MagasinUpdateComponent, MagasinDeleteDialogComponent, MagasinDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OhmymagMagasinModule {}
