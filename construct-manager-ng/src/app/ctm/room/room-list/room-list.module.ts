import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RoomListRoutingModule } from './room-list-routing.module';
import { RoomListComponent } from './room-list.component';
import { RoomListItemComponent } from './room-list-item/room-list-item.component';

@NgModule({
  declarations: [RoomListComponent, RoomListItemComponent],
  imports: [
    CommonModule,
    RoomListRoutingModule
  ]
})
export class RoomListModule { }
