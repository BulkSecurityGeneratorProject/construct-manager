import { IRoomCtm } from 'app/shared/model/room-ctm.model';

export interface IHomeCtm {
    id?: number;
    name?: string;
    description?: string;
    rooms?: IRoomCtm[];
}

export class HomeCtm implements IHomeCtm {
    constructor(public id?: number, public name?: string, public description?: string, public rooms?: IRoomCtm[]) {}
}
