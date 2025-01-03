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
      console.log("dobil")
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
