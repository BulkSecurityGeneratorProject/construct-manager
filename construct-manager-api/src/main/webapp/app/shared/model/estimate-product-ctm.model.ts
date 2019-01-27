export interface IEstimateProductCtm {
    id?: number;
    number?: number;
    price?: number;
    estimateId?: number;
    productId?: number;
    genericId?: number;
}

export class EstimateProductCtm implements IEstimateProductCtm {
    constructor(
        public id?: number,
        public number?: number,
        public price?: number,
        public estimateId?: number,
        public productId?: number,
        public genericId?: number
    ) {}
}
