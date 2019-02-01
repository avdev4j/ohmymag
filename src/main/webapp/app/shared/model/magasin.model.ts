import { IDepartement } from 'app/shared/model/departement.model';

export const enum MagasinStatus {
    OPEN = 'OPEN',
    CLOSED = 'CLOSED'
}

export interface IMagasin {
    id?: number;
    code?: string;
    name?: string;
    status?: MagasinStatus;
    departement?: IDepartement;
}

export class Magasin implements IMagasin {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public status?: MagasinStatus,
        public departement?: IDepartement
    ) {}
}
