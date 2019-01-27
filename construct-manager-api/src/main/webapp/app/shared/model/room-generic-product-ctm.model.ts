export const enum Unit {
    ML = 'ML',
    KG = 'KG',
    M = 'M'
}

export interface IRoomGenericProductCtm {
    id?: number;
    quantity?: number;
    quantityUnit?: Unit;
    roomId?: number;
    productId?: number;
}

export class RoomGenericProductCtm implements IRoomGenericProductCtm {
    constructor(
        public id?: number,
        public quantity?: number,
        public quantityUnit?: Unit,
        public roomId?: number,
        public productId?: number
    ) {}
}
