import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalVarService {

  readonly skavtkoApiUrl: string = 'http://89.168.78.91:80/v1';
  readonly modalPutFadeTime: number = 3000;

  constructor() { }
}
