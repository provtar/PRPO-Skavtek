import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalVarService } from './data/global-var.service';
import { Srecanje } from './data/user-data.service';
import { catchError, retry, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SrecanjaService {

constructor(private http: HttpClient, private globalVar: GlobalVarService) { }

public getSrecanje(id: number){
  const url = `${this.globalVar.skavtkoApiUrl}/srecanja/${id}`;

  return this.http.get<Srecanje>(url).pipe(retry(1), catchError(this.handleError));
}

public getSrecanjaPoParametrih(clanId: number | null = null, skupinaId: number | null = null,
  datumOd: Date | null = null, datumDo: Date | null = null){
  const url = `${this.globalVar.skavtkoApiUrl}/srecanja`;
  let params = new HttpParams();
  if(clanId)params.set('c', clanId.toString());
  if(skupinaId)params.set('s', skupinaId.toString());
  if(datumOd)params.set('od', datumOd.toISOString());
  if(datumDo)params.set('do', datumDo.toISOString());

  return this.http.get<Srecanje[]>(url, { params }).pipe(retry(1), catchError(this.handleError));
}

public postSrecanje(data: SrecanjePostData) {
    const url: string = `${this.globalVar.skavtkoApiUrl}/srecanja`;

    if(!data.datumOd)data.datumOd = null;
    if(!data.datumDo)data.datumDo = null;
    if(!data.kraj)data.kraj = null;
    if(!data.opis)data.opis = null;

    return this.http.post<Srecanje>(url, data).pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

public putSrecanje(data: SrecanjePutData) {
  const url: string = `${this.globalVar.skavtkoApiUrl}/srecanja`;

  if(!data.datumOd)data.datumOd = null;
  if(!data.datumDo)data.datumDo = null;
  if(!data.kraj)data.kraj = null;
  if(!data.opis)data.opis = null;

  return this.http.put<Srecanje>(url, data).pipe(
      retry(1),
      catchError(this.handleError)
    );
}

public deleteSrecanje(id: number){
  const url: string = `${this.globalVar.skavtkoApiUrl}/srecanja/${id}`;
  return this.http.delete(url).pipe(
    retry(1),
    catchError(this.handleError)
  );
}

private handleError(err : HttpErrorResponse){
      return throwError(() => err.error || err.statusText);
  }
}

export class SrecanjePostData {
  ime!: string;
  datumOd?: Date | null;
  datumDo?: Date | null;
  kraj?: string | null;
  opis?: string | null;
  idSkupine!: number;
}

export class SrecanjePutData {
  readonly id!: number;
  ime!: string;
  datumOd?: Date | null;
  datumDo?: Date | null;
  kraj?: string | null;
  opis?: string | null;
  readonly idSkupine!: number;
}
