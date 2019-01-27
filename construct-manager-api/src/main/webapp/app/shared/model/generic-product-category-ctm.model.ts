import { IGenericProductCtm } from 'app/shared/model/generic-product-ctm.model';

export interface IGenericProductCategoryCtm {
    id?: number;
    name?: string;
    description?: string;
    products?: IGenericProductCtm[];
}

export class GenericProductCategoryCtm implements IGenericProductCategoryCtm {
    constructor(public id?: number, public name?: string, public description?: string, public products?: IGenericProductCtm[]) {}
}
