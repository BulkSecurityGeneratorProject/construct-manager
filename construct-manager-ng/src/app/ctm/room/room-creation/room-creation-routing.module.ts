import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RoomCreationComponent} from "./room-creation.component";

const routes: Routes = [
  {
    path: '',
    component: RoomCreationComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RoomCreationRoutingModule { }
