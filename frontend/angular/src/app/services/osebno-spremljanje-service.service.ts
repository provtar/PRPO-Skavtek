import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, retry, throwError } from 'rxjs';
import { OsebnoSpremljanje } from './data/user-data.service';
import { GlobalVarService } from './data/global-var.service';

@Injectable({
  providedIn: 'root'
})
export class OsebnoSpremljanjeServiceService {

constructor(private http: HttpClient, private globalVar: GlobalVarService) { }

public getOsebnoSpremljanjePoClanu(clanId: number){
  const url = `${this.globalVar.skavtkoApiUrl}/osebno-spremljanje`;
  let params = new HttpParams();
  params.set('clanId', clanId.toString());

  return this.http.get<OsebnoSpremljanje[]>(url, { params }).pipe(retry(1), catchError(this.handleError));
}

public postOsebnoSpremljanje(data: OsebnoSpremljanjePostData) {
    const url: string = `${this.globalVar.skavtkoApiUrl}/osebno-spremljanje`;
    if(!data.vsebina)data.vsebina = null;

    return this.http.post<OsebnoSpremljanje>(url, data).pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  public putOsebnoSpremljanje(data: OsebnoSpremljanjePutData) {
    const url: string = `${this.globalVar.skavtkoApiUrl}/osebno-spremljanje`;

    if(!data.vsebina)data.vsebina = null;

    return this.http.put<OsebnoSpremljanje>(url, data).pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  public deleteOsebnoSpremljanje(id: number){
    const url: string = `${this.globalVar.skavtkoApiUrl}/osebno-spremljanje/${id}`;
    return this.http.delete(url).pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  private handleError(err : HttpErrorResponse){
      return throwError(() => err.error || err.statusText);
  }
}

export class OsebnoSpremljanjePostData {
  datum!: Date;
  naslov!: string;
  vsebina?: string | null;
  clanId!: number;
}

export class OsebnoSpremljanjePutData {
  id!: number;
  datum!: Date;
  naslov!: string;
  vsebina?: string | null;
}
