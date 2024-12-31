import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalVarService } from './global-var.service';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { ClanData } from './clan-data.service';
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public login(data: LoginData){
    const url = `${this.global.skavtkoApiUrl}/clani/login`;
    return this.http.post<ClanData>(url, data).pipe(retry(1),
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
