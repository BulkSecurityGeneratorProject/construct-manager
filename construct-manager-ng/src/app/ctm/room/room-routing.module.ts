import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RoomComponent} from "./room.component";

const routes: Routes = [
  {
    path: '',
    component: RoomComponent,
    children: [
      {
        path: '',
        loadChildren: './room-list/room-list.module#RoomListModule'
      },
      {
        path: 'create',
        loadChildren: './room-creation/room-creation.module#RoomCreationModule'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RoomRoutingModule { }
