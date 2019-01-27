import { Action } from '@ngrx/store';

export enum CtmRoomActionTypes {
  LoadCtmRooms = '[CtmRoom] Load CtmRooms'
}

export class LoadCtmRooms implements Action {
  readonly type = CtmRoomActionTypes.LoadCtmRooms;
}

export type CtmRoomActions = LoadCtmRooms;
