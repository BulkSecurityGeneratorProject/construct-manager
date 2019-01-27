import { IProductCtm } from 'app/shared/model/product-ctm.model';

export interface IShopCtm {
    id?: number;
    name?: string;
    description?: string;
    products?: IProductCtm[];
}

export class ShopCtm implements IShopCtm {
    constructor(public id?: number, public name?: string, public description?: string, public products?: IProductCtm[]) {}
}
