export const enum Unit {
    ML = 'ML',
    KG = 'KG',
    M = 'M'
}

export interface IProductCtm {
    id?: number;
    name?: string;
    description?: string;
    packaging?: number;
    packagingUnit?: Unit;
    link?: string;
    price?: number;
    reference?: string;
    shopId?: number;
}

export class ProductCtm implements IProductCtm {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public packaging?: number,
        public packagingUnit?: Unit,
        public link?: string,
        public price?: number,
        public reference?: string,
        public shopId?: number
    ) {}
}
