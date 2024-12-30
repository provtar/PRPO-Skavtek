import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClanDataService } from '../services/clan-data.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  constructor(private readonly clanDS: ClanDataService){}

  clani : Clan[] = [
    {
      id: 4,
      ime: "Peter",
      priimek: "Savron"
    },
    {
      id: 2,
      ime: "Peter",
      priimek: "Klepec"
    },
    {
      id: 3,
      ime: "Peter",
      priimek: "Pan"
    }
  ];

  dobiClana(id: number){
    // this.clani.push({
    //   _id: 1,
    //   ime: "Kdor",
    //   priimek: "Koli"
    // });
    this.clanDS.getClan(id).subscribe((clan) => (this.clani.push(clan)));
  };


  clan? : Clan;
}

export class Clan{
  id!: number;
  ime!: string;
  priimek!: string;
  steg?: string;
  skavtskoIme?: string;
}
