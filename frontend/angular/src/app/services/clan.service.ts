import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { GlobalVarService } from './data/global-var.service';
import { Clan } from './data/user-data.service';

@Injectable({
  providedIn: 'root'
})
export class ClanDataService{


  constructor(private readonly http: HttpClient, private readonly gloabalVars: GlobalVarService) {
  }

  public register(){

  }

  // Vrne Observable, ne ze dobljeno entiteto, tako je lazje obdelati rezulatate povezane z odgovori, return type se klice z subscribe
  public getClan(id: number): Observable<Clan>{
    const url: string = `${this.gloabalVars.skavtkoApiUrl}/clani/${id}`;
    return this.http.get<Clan>(url).pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  public getVarovanci(voditeljId: number): Observable<Clan[]>{
    const url: string = `${this.gloabalVars.skavtkoApiUrl}/clani/${voditeljId}/varovanci`;
    return this.http.get<Clan[]>(url).pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  public postClan(data: ClanPostData, master: number) : Observable<Clan> {
    const url: string = `${this.gloabalVars.skavtkoApiUrl}/clani`;

    if(!data.skavtskoIme) data.skavtskoIme = null;
    if(!data.steg) data.steg = null;

    const headers = new HttpHeaders({
      'master': `${master}`,
    })

    return this.http.post<Clan>(url, data, {headers: headers}).pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  public putClan(data: ClanPutData) {
    const url: string = `${this.gloabalVars.skavtkoApiUrl}/clani`;

    if(!data.skavtskoIme) data.skavtskoIme = null;
    if(!data.steg) data.steg = null;

    return this.http.put<Clan>(url, data).pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  public deleteClan(id: number){
    const url: string = `${this.gloabalVars.skavtkoApiUrl}/clani/${id}`;
    return this.http.delete<Clan>(url).pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  private handleError(err : HttpErrorResponse){
    return throwError(() => err.error || err.statusText);
  }
}



export class ClanPostData {
  ime!: string;
  priimek!: string;
  steg?: string | null;
  skavtskoIme?: string | null;
}

export class ClanPutData extends Clan {}
