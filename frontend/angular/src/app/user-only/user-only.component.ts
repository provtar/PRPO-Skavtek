import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDataService } from '../services/user-data.service';

@Component({
  selector: 'app-user-only',
  standalone: true,
  imports: [],
  templateUrl: './user-only.component.html',
  styleUrl: './user-only.component.css'
})
export class UserOnlyComponent implements OnInit {
  constructor(protected router: Router, protected data: UserDataService){}

  ngOnInit(): void {
    if(!localStorage.getItem('user')) this.router.navigate(['login']);
  }

}
