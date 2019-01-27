import { IEstimateCtm } from 'app/shared/model/estimate-ctm.model';

export interface IDivisionCtm {
    id?: number;
    name?: string;
    description?: string;
    estimates?: IEstimateCtm[];
}

export class DivisionCtm implements IDivisionCtm {
    constructor(public id?: number, public name?: string, public description?: string, public estimates?: IEstimateCtm[]) {}
}
