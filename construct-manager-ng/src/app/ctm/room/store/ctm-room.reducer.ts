import { Action } from '@ngrx/store';
import { CtmRoomActions, CtmRoomActionTypes } from './ctm-room.actions';

export interface State {

}

export const initialState: State = {

};

export function reducer(state = initialState, action: CtmRoomActions): State {
  switch (action.type) {

    case CtmRoomActionTypes.LoadCtmRooms:
      return state;


    default:
      return state;
  }
}
