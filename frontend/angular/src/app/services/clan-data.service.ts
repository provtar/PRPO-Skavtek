import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, from, Observable, retry, throwError } from 'rxjs';
import { GlobalVarService } from './global-var.service';
import { Clan } from './user-data.service';

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

  public postClan(data: ClanPostData, master: number) : Observable<Clan> {
    const url: string = `${this.gloabalVars.skavtkoApiUrl}/clani`;

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

    return this.http.put(url, data).pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  public deleteClan(id: number): Observable<Clan>{
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
  steg!: string;
  skavtskoIme!: string;
}

export class ClanPutData extends Clan {}
