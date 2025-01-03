import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalVarService {

  readonly skavtkoApiUrl: string = 'http://localhost:8080/v1';
  readonly modalPutFadeTime: number = 3000;

  constructor() { }
}
