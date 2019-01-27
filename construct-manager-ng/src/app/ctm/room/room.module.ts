import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RoomRoutingModule } from './room-routing.module';
import { RoomComponent } from './room.component';
import {StoreModule} from "@ngrx/store";
import * as fromStore from './store';
import {EffectsModule} from "@ngrx/effects";
import {CtmRoomEffects} from "./store";

@NgModule({
  declarations: [RoomComponent],
  imports: [
    CommonModule,
    RoomRoutingModule,
    StoreModule.forFeature('ctm-room', fromStore.reducer),
    EffectsModule.forFeature([CtmRoomEffects])
  ]
})
export class RoomModule { }
