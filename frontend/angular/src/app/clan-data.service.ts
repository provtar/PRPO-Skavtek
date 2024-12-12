import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { Clan } from './register/register.component';

@Injectable({
  providedIn: 'root'
})
export class ClanDataService {

  constructor(private readonly http: HttpClient) {}

  private readonly apiUrl = "http://localhost:8080/v1";

  public getClan(id: number): Observable<Clan>{
    const url: string = `${this.apiUrl}/clani/${id}`;
    return this.http.get<Clan>(url).pipe(retry(1), catchError(this.handleError));
  }

  private handleError(err : HttpErrorResponse){
    return throwError(() => err.error || err.statusText);
  }
}
