import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SkupinaService {

  constructor() { }
}

export class ClanSkupine {
  id!: number;
  ime!: string;
  priimek!: string;
  steg!: string;
}
