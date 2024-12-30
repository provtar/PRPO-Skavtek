import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalVarService {

  skavtkoApiUrl: string = 'http://localhost:8082/v1';

  constructor() { }
}
