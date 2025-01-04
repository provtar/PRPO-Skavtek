import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalVarService } from './data/global-var.service';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { Clan } from './data/user-data.service';
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public login(data: LoginData){
    const url = `${this.global.skavtkoApiUrl}/clani/login`;
    return this.http.post<Clan>(url, data).pipe(retry(1),
      catchError( (error) => {
        return throwError( () => error);
      })
    )
  }

  constructor(private http: HttpClient,
    private global: GlobalVarService) { }
}

export class LoginData {
  email!: string;
  password!: string;
}
