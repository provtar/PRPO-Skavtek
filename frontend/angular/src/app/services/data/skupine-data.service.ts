import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Clan, ClanSkupine, Skupina } from './user-data.service';

@Injectable({
  providedIn: 'root'
})
export class SkupineDataService { //ima smisla? itak vse skupine so pri clanu, detajli pa se sproti nalozijo samo na eni strani, za put form rabis posodobiti, in tudi za delte, ob koncu forma spet uploadaj data
    // private skupinaSubject = new BehaviorSubject<Skupina | undefined>(undefined);
    // private skupinaInitialized : boolean = false;
    // skupina$ = this.skupinaSubject.asObservable();

    // skupinaNotInit() {
    //   return !this.skupinaInitialized;
    // }

    // initSkupina(skupina: Skupina){
    //   if(!this.skupinaInitialized){
    //     this.skupinaSubject.next(skupina);
    //     this.skupinaInitialized = true;
    //   }
    // }

    // public dodajVarovanca(varovanec: Clan){
    //   const curr = this.varovanciSubject.value;
    //   const updt = [...curr, varovanec];
    //   this.varovanciSubject.next(updt);
    // }

    // public posodobiVarovanca(varovanec: Clan){
    //   var vsiVar = this.varovanciSubject.value;
    //   var target = vsiVar.find(varo => varo.id === varovanec.id);

    //   if(target){
    //     target.ime = varovanec.ime;
    //     target.priimek = varovanec.priimek;
    //     target.steg = varovanec.steg;
    //     target.skavtskoIme = varovanec.skavtskoIme;
    //   }
    //   this.varovanciSubject.next(vsiVar);
    // }

    // public odstraniVarovanca(id: number) {
    //   const tabla = this.varovanciSubject.value;
    //   var novaTabla = tabla.filter(clan => clan.id != id);
    //   this.varovanciSubject.next(novaTabla);
    // }

    // private claniSkupineSubject = new BehaviorSubject<ClanSkupine[] | undefined>(undefined);
    // private claniInitialized : boolean = false;
    // claniSkupine$ = this.claniSkupineSubject.asObservable();

    // claniSkupineNotInit() {
    //   return !this.claniInitialized;
    // }

    // initClaniSkupine(clani: ClanSkupine[]){
    //   if(!this.claniInitialized){
    //     this.claniSkupineSubject.next(clani);
    //     this.claniInitialized = true;
    //   }
    // }

    // public dodajVarovanca(varovanec: Clan){
    //   const curr = this.varovanciSubject.value;
    //   const updt = [...curr, varovanec];
    //   this.varovanciSubject.next(updt);
    // }

    // public posodobiVarovanca(varovanec: Clan){
    //   var vsiVar = this.varovanciSubject.value;
    //   var target = vsiVar.find(varo => varo.id === varovanec.id);

    //   if(target){
    //     target.ime = varovanec.ime;
    //     target.priimek = varovanec.priimek;
    //     target.steg = varovanec.steg;
    //     target.skavtskoIme = varovanec.skavtskoIme;
    //   }
    //   this.varovanciSubject.next(vsiVar);
    // }

    // public odstraniVarovanca(id: number) {
    //   const tabla = this.varovanciSubject.value;
    //   var novaTabla = tabla.filter(clan => clan.id != id);
    //   this.varovanciSubject.next(novaTabla);
    // }

    // public clear()
}
