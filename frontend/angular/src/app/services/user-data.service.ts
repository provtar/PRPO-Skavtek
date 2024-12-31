import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {

  constructor() { }
}

//tukaj so tudi vsi razredi

export class Clan {
  id!: number;
  ime!: string;
  priimek!: string;
  steg!: string;
  skavtskoIme!: string;
}

export class Skupina{
  id!: number;
  ime!: string;
  opis!: string;
  povezave!: Povezava[];
}

// OPOMBA - bodi pozoren, da ko inicializiras convertas v Date
export class Srecanje {
  id!: number;
  ime!: string;
  datumOd!: Date;
  datumDo!: Date;
  kraj!: string;
  opis!: string;
  belezenje!: boolean;
  idSkupine!: number;
}

export class Prisotnost {
  id!: number;
  prisotnost!: string;
  opomba!: string;
  idClana!: number;
  imeClana!: string;
  priiemkClana!: string;
  idSrecanja!: number;
  imeSrecanja!: string;
}

export class Povezava {
  name!: string;
  link!: string;
}

export class ClanSkupine {
  id!: number;
  ime!: string;
  priimek!: string;
  steg!: string;
}
