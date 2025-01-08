import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { SrecanjaService } from '../../../services/srecanja.service';
import { Srecanje } from '../../../services/data/user-data.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-srecanja-skupine',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './srecanja-skupine.component.html',
  styleUrl: './srecanja-skupine.component.css'
})
export class SrecanjaSkupineComponent {
  @Input() skupinaId!: number;

  constructor(private srecanjeService : SrecanjaService, private router : Router) {}
  srecanja! : Srecanje[];
  initialized : boolean = false;

  ngOnInit(){
    this.srecanjeService.getSrecanjaPoParametrih(null, this.skupinaId).subscribe(
      (response) => {
        this.srecanja = response;
        this.initialized = true;
      }
    )
  }

  refresh(){
    this.srecanjeService.getSrecanjaPoParametrih(null, this.skupinaId).subscribe(
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
