import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {
  private varovanciSubject = new BehaviorSubject<Clan[]>([]);
  // TODO inicializacija varovancev
  varovanci$ = this.varovanciSubject.asObservable();

  dodajVarovanca(varovanec: Clan){
    const curr = this.varovanciSubject.value;
    const updt = [...curr, varovanec];
    this.varovanciSubject.next(updt);
  }

  posodobiVarovanca(varovanec: Clan){
    var vsiVar = this.varovanciSubject.value;
    var target = vsiVar.find(varo => varo.id === varovanec.id);

    if(target){
      target.ime = varovanec.ime;
      target.priimek = varovanec.priimek;
      target.steg = varovanec.steg;
      target.skavtskoIme = varovanec.skavtskoIme;
    }
    this.varovanciSubject.next(vsiVar);
  }

  odstraniVarovanca(id: number) {
    const tabla = this.varovanciSubject.value;
    var novaTabla = tabla.filter(clan => clan.id != id);
    this.varovanciSubject.next(novaTabla);
  }

  constructor() { }
}

//tukaj so tudi vsi razredi

export class Clan {
  readonly id!: number;
  ime!: string;
  priimek!: string;
  steg?: string | null;
  skavtskoIme?: string | null;
}

export class Skupina{
  readonly id!: number;
  ime!: string;
  opis?: string | null;
  povezave!: Povezava[];
}

// OPOMBA - bodi pozoren, da ko inicializiras convertas v Date
export class Srecanje {
  readonly id!: number;
  ime!: string;
  datumOd?: Date | null;
  datumDo?: Date | null;
  kraj?: string | null;
  opis?: string | null;
  belezenje!: boolean;
  readonly idSkupine!: number;
}

export class Prisotnost {
  readonly id!: number;
  prisotnost!: string;
  opomba?: string | null;
  readonly idClana!: number;
  imeClana!: string;
  priimekClana!: string;
  readonly idSrecanja!: number;
  imeSrecanja!: string;
}

export class Povezava {
  name!: string;
  link!: string;
}

export class ClanSkupine {
  readonly clanId!: number;
  ime!: string;
  priimek!: string;
  steg?: string | null;
}
