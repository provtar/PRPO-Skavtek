import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalVarService } from './global-var.service';
import { catchError, retry, throwError } from 'rxjs';
import { Prisotnost } from './user-data.service';

@Injectable({
  providedIn: 'root'
})
export class PrisotnostiService {

constructor(private http: HttpClient, private globalVar: GlobalVarService) { }

public getPrisotnostiSrecanja(srecanjeId: number){
  const url = `${this.globalVar.skavtkoApiUrl}/prisotnosti`
  let params = new HttpParams().set('srecanje', srecanjeId.toString());

  return this.http.get<Prisotnost[]>(url, { params }).pipe(retry(1), catchError(this.handleError));
}

public getPrisotnostiPoClanu(clanId: number){
  const url = `${this.globalVar.skavtkoApiUrl}/prisotnosti`
  let params = new HttpParams().set('clan', clanId.toString());

  return this.http.get<Prisotnost[]>(url, { params }).pipe(retry(1), catchError(this.handleError));
}

public getPrisotnostiPoSkupini(skupinaId: number){
  const url = `${this.globalVar.skavtkoApiUrl}/prisotnosti`
  let params = new HttpParams().set('skupina', skupinaId.toString());

  return this.http.get<Prisotnost[]>(url, { params }).pipe(retry(1), catchError(this.handleError));
}

public postSrecanje(srecanjeId: number) {
    const url: string = `${this.globalVar.skavtkoApiUrl}/prisotnosti/srecanja/${srecanjeId}`;

    return this.http.post<Prisotnost[]>(url, {}).pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

public putPrisotnosti(prisotnosti: PrisotnostPutData[]){
  const url: string = `${this.globalVar.skavtkoApiUrl}/prisotnosti`;

  //vrne vse dodane clane
  return this.http.put<Prisotnost[]>(url, prisotnosti).pipe(
      retry(1),
      catchError(this.handleError)
  );
}

public deleteNekajPrisotnosti(body: number[]){
    const url: string = `${this.globalVar.skavtkoApiUrl}/prisotnosti`;
    return this.http.delete(url, {body}).pipe(
      retry(1),
      catchError(this.handleError)
    );
}

public deleteVsePrisotnosti(srecanjeId: number){
  const url: string = `${this.globalVar.skavtkoApiUrl}/srecanja/${srecanjeId}`;
  return this.http.delete(url).pipe(
    retry(1),
    catchError(this.handleError)
  );
}

private handleError(err : HttpErrorResponse){
      return throwError(() => err.error || err.statusText);
  }
}

export class PrisotnostPutData {
  readonly id!: number;
  prisotnost!: string;
  opomba?: string | null;
}
