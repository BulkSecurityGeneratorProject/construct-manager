export interface IRoomSizeCtm {
    id?: number;
    floorSize?: number;
    wallSize?: number;
    roomId?: number;
}

export class RoomSizeCtm implements IRoomSizeCtm {
    constructor(public id?: number, public floorSize?: number, public wallSize?: number, public roomId?: number) {}
}
