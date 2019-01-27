import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CtmComponent} from "./ctm.component";

const routes: Routes = [
  {
    path: '',
    component: CtmComponent,
    children: [
      {
        path: '',
        redirectTo: 'room',
        pathMatch: 'full'
      },
      {
        path: 'room',
        loadChildren: './room/room.module#RoomModule'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CtmRoutingModule { }
