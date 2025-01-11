import { Component, Input } from '@angular/core';
import { SrecanjaService } from '../../../services/srecanja.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Srecanje } from '../../../services/data/user-data.service';

@Component({
  selector: 'app-srecanja-clana',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './srecanja-clana.component.html',
  styleUrl: './srecanja-clana.component.css'
})
export class SrecanjaClanaComponent {
  @Input() clanId!: number;

  constructor(private srecanjeService : SrecanjaService, private router : Router) {}
  srecanja! : Srecanje[];
  initialized : boolean = false;

  ngOnInit(){
    this.srecanjeService.getSrecanjaPoParametrih(this.clanId).subscribe(
      (response) => {
        this.srecanja = response;
        this.initialized = true;
      }
    )
  }

  refresh(){
    this.srecanjeService.getSrecanjaPoParametrih(this.clanId).subscribe(
      (response) => {
        this.srecanja = response;
        this.initialized = true;
      }
    )
  }

  toSrecanje(srecanjeId : number){
    this.router.navigate(['srecanje'], {
      queryParams: { srecanjeId: srecanjeId}
    })
  }
}
