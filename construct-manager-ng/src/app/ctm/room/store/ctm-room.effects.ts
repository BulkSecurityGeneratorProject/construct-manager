import { Injectable } from '@angular/core';
import { Actions, Effect, ofType } from '@ngrx/effects';
import { CtmRoomActionTypes } from './ctm-room.actions';

@Injectable()
export class CtmRoomEffects {

  @Effect()
  loadCtmRooms$ = this.actions$.pipe(ofType(CtmRoomActionTypes.LoadCtmRooms));

  constructor(private actions$: Actions) {}
}
