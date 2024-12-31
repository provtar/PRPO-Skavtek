import { Injectable } from '@angular/core';
import { ClanData } from './clan-data.service';

@Injectable({
  providedIn: 'root'
})
export class GlobalVarService {

  skavtkoApiUrl: string = 'http://localhost:8080/v1';
  user: ClanData | undefined = undefined;
  constructor() { }
}
