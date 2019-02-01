import { IMagasin } from 'app/shared/model/magasin.model';

export interface IDepartement {
    id?: number;
    code?: string;
    name?: string;
    codes?: IMagasin[];
}

export class Departement implements IDepartement {
    constructor(public id?: number, public code?: string, public name?: string, public codes?: IMagasin[]) {}
}
