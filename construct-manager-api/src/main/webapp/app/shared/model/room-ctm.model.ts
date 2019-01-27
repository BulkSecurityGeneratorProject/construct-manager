import { IRoomGenericProductCtm } from 'app/shared/model/room-generic-product-ctm.model';

export interface IRoomCtm {
    id?: number;
    name?: string;
    description?: string;
    homeId?: number;
    products?: IRoomGenericProductCtm[];
    typeId?: number;
}

export class RoomCtm implements IRoomCtm {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public homeId?: number,
        public products?: IRoomGenericProductCtm[],
        public typeId?: number
    ) {}
}
