export interface IRoomTypeCtm {
    id?: number;
    name?: string;
}

export class RoomTypeCtm implements IRoomTypeCtm {
    constructor(public id?: number, public name?: string) {}
}
