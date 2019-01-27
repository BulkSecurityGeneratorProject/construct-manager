export interface IGenericProductCtm {
    id?: number;
    name?: string;
    description?: string;
    genericProductCategoryId?: number;
}

export class GenericProductCtm implements IGenericProductCtm {
    constructor(public id?: number, public name?: string, public description?: string, public genericProductCategoryId?: number) {}
}
