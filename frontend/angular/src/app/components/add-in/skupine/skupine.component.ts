import { Component, Input } from '@angular/core';
import { SkupinaService } from '../../../services/skupina.service';
import { Skupina } from '../../../services/data/user-data.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-skupine',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './skupine.component.html',
  styleUrl: './skupine.component.css'
})
export class SkupineComponent {
  @Input() clanId! : number;

  skupine! : Skupina[];
  initialized : boolean = false;

  constructor(private skupinaService : SkupinaService, private router : Router) {}

  ngOnInit() {
    this.skupinaService.getSkupinePoClanu(this.clanId).subscribe(
      (response) => {
        this.skupine = response;
        this.initialized = true;
    })
  }

  refresh() {
    this.skupinaService.getSkupinePoClanu(this.clanId).subscribe(
      (response) => {
        this.skupine = response;
    })
  }

  toSkupina(skupinaId: number){
    this.router.navigate(['/skupina'], {
      queryParams: { skupinaId: skupinaId}
    })
  }
}
