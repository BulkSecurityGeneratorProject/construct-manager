import { IEstimateProductCtm } from 'app/shared/model/estimate-product-ctm.model';

export interface IEstimateCtm {
    id?: number;
    name?: string;
    description?: string;
    total?: number;
    divisionId?: number;
    products?: IEstimateProductCtm[];
}

export class EstimateCtm implements IEstimateCtm {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public total?: number,
        public divisionId?: number,
        public products?: IEstimateProductCtm[]
    ) {}
}
