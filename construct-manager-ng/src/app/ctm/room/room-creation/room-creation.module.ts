import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RoomCreationRoutingModule } from './room-creation-routing.module';
import { RoomCreationComponent } from './room-creation.component';

@NgModule({
  declarations: [RoomCreationComponent],
  imports: [
    CommonModule,
    RoomCreationRoutingModule
  ]
})
export class RoomCreationModule { }
