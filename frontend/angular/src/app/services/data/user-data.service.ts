import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {

  user!: Clan;
  public initUser() {
    this.user = JSON.parse(localStorage.getItem('user')!);
  }
  private varovanciSubject = new BehaviorSubject<Clan[]>([]);
  private varovanciInitialized : boolean = false;
  varovanci$ = this.varovanciSubject.asObservable();

  varovanciNotInit() {
    return !this.varovanciInitialized;
  }

  initVarovanci(varovanci: Clan[]){
    if(!this.varovanciInitialized){
      this.varovanciSubject.next(varovanci);
      this.varovanciInitialized = true;
    }
  }

  public dodajVarovanca(varovanec: Clan){
    const curr = this.varovanciSubject.value;
    const updt = [...curr, varovanec];
    this.varovanciSubject.next(updt);
  }

  public posodobiVarovanca(varovanec: Clan){
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

  public odstraniVarovanca(id: number) {
    const tabla = this.varovanciSubject.value;
    var novaTabla = tabla.filter(clan => clan.id != id);
    this.varovanciSubject.next(novaTabla);
  }



  private mojeSkupineSubject = new BehaviorSubject<Skupina[]>([]);
  private mojeSkupineInitialized = false;
  mojeSkupine$ = this.mojeSkupineSubject.asObservable();

  mojeSkupineNotInit() {
    return !this.mojeSkupineInitialized;
  }

  initMojeSkupine(skupine: Skupina[]){
    if(!this.mojeSkupineInitialized){
      this.mojeSkupineSubject.next(skupine);
      this.mojeSkupineInitialized = true;
    }
  }

  public dodajSkupino(skupina: Skupina){
    const curr = this.mojeSkupineSubject.value;
    const updt = [...curr, skupina];
    this.mojeSkupineSubject.next(updt);
  }

  public posodobiSkupino(skupina: Skupina){
    const skupine = this.mojeSkupineSubject.value;
    const target = skupine.find(s => s.id === skupina.id);

    if(target){
      target.ime = skupina.ime;
      target.opis = skupina.opis;
      target.povezave = skupina.povezave;
    }
    this.mojeSkupineSubject.next(skupine);
  }

  public odstraniSkupino(id: number) {
    const tabla = this.mojeSkupineSubject.value;
    var novaTabla = tabla.filter(skupina => skupina.id != id);
    this.mojeSkupineSubject.next(novaTabla);
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
  datumOd!: Date;
  datumDo!: Date;
  kraj?: string | null;
  opis?: string | null;
  belezenje!: boolean;
  readonly idSkupine!: number;
  imeSkupine!: string;
  temperatura?: number;
  padavine?: number;
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

export class OsebnoSpremljanje {
  id!: number;
  datum!: Date;
  naslov!: string;
  vsebina?: string;
  clanId!: number;
}

export class Termin {
  id! : number;
  datumOd!: Date;
  datumDo!: Date;
  tip!: string;
  clanId!: number;
}
