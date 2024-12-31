import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { GlobalVarService } from './global-var.service';

@Injectable({
  providedIn: 'root'
})
export class ClanDataService {

  constructor(private readonly http: HttpClient, private readonly gloabalVars: GlobalVarService) {}

  public register(){

  }

  public getClan(id: number): Observable<ClanData>{
    const url: string = `${this.gloabalVars.skavtkoApiUrl}/clani/${id}`;
    return this.http.get<ClanData>(url).pipe(retry(1), catchError(this.handleError));
  }

  private handleError(err : HttpErrorResponse){
    return throwError(() => err.error || err.statusText);
  }
}

export class ClanData {
  id!: number;
  ime!: string;
  priimek!: string;
  steg!: string;
  skavtskoIme!: string;
}
