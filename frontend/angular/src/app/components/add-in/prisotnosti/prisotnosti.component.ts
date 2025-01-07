import { Component, Input } from '@angular/core';
import { PrisotnostiService } from '../../../services/prisotnosti.service';
import { Prisotnost } from '../../../services/data/user-data.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-prisotnosti',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './prisotnosti.component.html',
  styleUrl: './prisotnosti.component.css'
})
export class PrisotnostiComponent {
  @Input() srecanjeId! : number;

  constructor(private prisotnostiService: PrisotnostiService){}
  prisotnosti!: Prisotnost[];
  initialized: boolean = false;

  ngOnInit(){
    this.prisotnostiService.getPrisotnostiSrecanja(this.srecanjeId).subscribe(
      (response) => {
        this.prisotnosti = response;
        this.initialized = true;
      }
    );
  }

  refresh(){
    this.prisotnostiService.getPrisotnostiSrecanja(this.srecanjeId).subscribe(
      (response) => {
        this.prisotnosti = response;
      }
    );
  }
}