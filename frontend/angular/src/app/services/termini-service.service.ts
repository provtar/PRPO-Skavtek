import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, retry, throwError } from 'rxjs';
import { GlobalVarService } from './data/global-var.service';
import { Termin } from './data/user-data.service';
import { query } from '@angular/animations';

@Injectable({
  providedIn: 'root'
})
export class TerminiServiceService {



  constructor(private http: HttpClient, private globalVar: GlobalVarService) { }

  public getTermin(datOd: string, datDo: string, clanID: number){
    const url = `${this.globalVar.skavtkoApiUrl}/termini`;
    let params = new HttpParams().set('od', datOd).set('do', datDo).set('clanId', clanID.toString());
    return this.http.get<Termin[]>(url, {params}).pipe(retry(1), catchError(this.handleError));
  }

  public postTermini(clanId: number,  datumOd: string, datumDo: string, termini : Termin[]){
      const url = `${this.globalVar.skavtkoApiUrl}/termini`;
      let params = new HttpParams().set('od', datumOd).set('do', datumDo).set('clanId', clanId.toString());

    return this.http.post<Termin[]>(url, termini, { params }).pipe(retry(1), catchError(this.handleError));
  }

  private handleError(err : HttpErrorResponse){
        return throwError(() => err.error || err.statusText);
  }
}
