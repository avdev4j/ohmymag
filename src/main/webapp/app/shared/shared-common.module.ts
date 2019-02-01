import { NgModule } from '@angular/core';

import { OhmymagSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [OhmymagSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [OhmymagSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class OhmymagSharedCommonModule {}
