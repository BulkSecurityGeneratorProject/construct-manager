import { NgModule } from '@angular/core';

import { ConstructManagerApiSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ConstructManagerApiSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ConstructManagerApiSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ConstructManagerApiSharedCommonModule {}
