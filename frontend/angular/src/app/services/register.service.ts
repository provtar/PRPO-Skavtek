import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalVarService } from './global-var.service';
import { catchError, retry, throwError } from 'rxjs';
import { ClanData } from './clan-data.service';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  public register(data: RegisterData){
    const url = `${this.global.skavtkoApiUrl}/clani/register`;
    return this.http.post<ClanData>(url, data).pipe(retry(1),
      catchError( (error) => {
        return throwError( () => error);
      })
    )
  }

  constructor(private http: HttpClient,
    private global: GlobalVarService) { }
}

export class RegisterData {
  ime!: string;
  priimek!: string;
  email!: string;
  password!: string;
}
