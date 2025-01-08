import { Component, Input } from '@angular/core';
import { Clan } from '../../../services/data/user-data.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-clani',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './clani.component.html',
  styleUrl: './clani.component.css'
})
export class ClaniComponent {
  @Input() clani! : Clan[]

  constructor(private router : Router){}

  toClan(clanId : number){
    this.router.navigate(['/clan'], {
      queryParams: {clanId: clanId}
    })
  }
}
