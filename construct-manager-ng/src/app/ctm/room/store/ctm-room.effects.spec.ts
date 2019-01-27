import { TestBed, inject } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Observable } from 'rxjs';

import { CtmRoomEffects } from './ctm-room.effects';

describe('CtmRoomEffects', () => {
  let actions$: Observable<any>;
  let effects: CtmRoomEffects;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        CtmRoomEffects,
        provideMockActions(() => actions$)
      ]
    });

    effects = TestBed.get(CtmRoomEffects);
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });
});
