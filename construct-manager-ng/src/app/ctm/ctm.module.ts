import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CtmRoutingModule } from './ctm-routing.module';
import { CtmComponent } from './ctm.component';

@NgModule({
  declarations: [CtmComponent],
  imports: [
    CommonModule,
    CtmRoutingModule
  ]
})
export class CtmModule { }
