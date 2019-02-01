import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'magasin',
                loadChildren: './magasin/magasin.module#OhmymagMagasinModule'
            },
            {
                path: 'departement',
                loadChildren: './departement/departement.module#OhmymagDepartementModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OhmymagEntityModule {}
