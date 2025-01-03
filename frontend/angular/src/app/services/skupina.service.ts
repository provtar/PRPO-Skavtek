import { query } from '@angular/animations';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ClanSkupine, Povezava, Skupina } from './user-data.service';
import { UrlCodec } from '@angular/common/upgrade';
import { GlobalVarService } from './global-var.service';
import { catchError, retry, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SkupinaService {

constructor(private http: HttpClient, private globalVar: GlobalVarService) { }

public getSkupina(id: number){
  const url = `${this.globalVar.skavtkoApiUrl}/skupine/${id}`;

  return this.http.get<Skupina>(url).pipe(retry(1), catchError(this.handleError));
}

public getSkupinePoClanu(clanId: number){
  const url = `${this.globalVar.skavtkoApiUrl}/skupine`
  let params = new HttpParams().set('clanId', clanId.toString());

  return this.http.get<Skupina[]>(url, { params }).pipe(retry(1), catchError(this.handleError));
}

public postSkupina(data: SkupinaPostData) {
    const url: string = `${this.globalVar.skavtkoApiUrl}/skupine`;

    if(!data.opis)data.opis = null;

    return this.http.post<Skupina>(url, data).pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

public putSkupina(data: SkupinaPutData) {
  const url: string = `${this.globalVar.skavtkoApiUrl}/skupine`;

  if(!data.opis)data.opis = null;

  return this.http.put<Skupina>(url, data).pipe(
      retry(1),
      catchError(this.handleError)
    );
}

public deleteSkupina(id: number){
  const url: string = `${this.globalVar.skavtkoApiUrl}/skupine/${id}`;
  return this.http.delete(url).pipe(
    retry(1),
    catchError(this.handleError)
  );
}

public getClaniSkupine(id: number){
  const url = `${this.globalVar.skavtkoApiUrl}/skupine/${id}/clani`

  return this.http.get<ClanSkupine[]>(url).pipe(retry(1), catchError(this.handleError));
}

// TODO to bo tezko spravit v form preverit kdo je ze v skupini, kdo ni se
// Ideja: dvakrat filtriras vse svoje varovance:
public putClaneSkupine(idSkupine: number, clani: number[]){
  const url: string = `${this.globalVar.skavtkoApiUrl}/skupine/${idSkupine}/clani`;

  //vrne vse dodane clane
  return this.http.put<ClanSkupine[]>(url, clani).pipe(
      retry(1),
      catchError(this.handleError)
  );
}

public deleteClaneSkupine(id: number, body: number[]){
    const url: string = `${this.globalVar.skavtkoApiUrl}/skupine/${id}/clani`;
    return this.http.delete(url, {body}).pipe(
      retry(1),
      catchError(this.handleError)
    );
}

public putEnegaClanaSkupine(idSkupine: number, clan: number){
  const url: string = `${this.globalVar.skavtkoApiUrl}/skupine/${idSkupine}/clani/${clan}`;

  //vrne vse dodane clane
  return this.http.put<ClanSkupine>(url, {}).pipe(
      retry(1),
      catchError(this.handleError)
  );
}

public deleteEnegaClanaSkupine(id: number, clan: number){
    const url: string = `${this.globalVar.skavtkoApiUrl}/skupine/${id}/clani/${clan}`;
    return this.http.delete(url).pipe(
      retry(1),
      catchError(this.handleError)
    );
}

  private handleError(err : HttpErrorResponse){
      return throwError(() => err.error || err.statusText);
  }
}

export class SkupinaPostData {
  ime!: string;
  opis: string | null = null;
  povezave!: Povezava[];
}

export class SkupinaPutData {
  readonly id!: number;
  ime!: string;
  opis: string | null = null;
  povezave!: Povezava[];
}
