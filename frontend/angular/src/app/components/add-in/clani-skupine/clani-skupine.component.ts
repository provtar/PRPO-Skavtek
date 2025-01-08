import { Component, Input } from '@angular/core';
import { SkupinaService } from '../../../services/skupina.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Clan, ClanSkupine } from '../../../services/data/user-data.service';
import { ClanSkupineDeleteOneButtonComponent } from "../../modal-button/clan-skupine-delete-one-button/clan-skupine-delete-one-button.component";

@Component({
  selector: 'app-clani-skupine',
  standalone: true,
  imports: [CommonModule, ClanSkupineDeleteOneButtonComponent],
  templateUrl: './clani-skupine.component.html',
  styleUrl: './clani-skupine.component.css'
})
export class ClaniSkupineComponent {
  @Input() skupinaId!: number;

  constructor(private skupinaService: SkupinaService, private router: Router) {}
  claniSkupine!: ClanSkupine[];
  initialized: boolean = false;

  ngOnInit(){
    this.skupinaService.getClaniSkupine(this.skupinaId).subscribe(
      (response) => {
        this.claniSkupine=response;
        this.initialized = true;
      }
    )
  }

  refresh(){
    this.skupinaService.getClaniSkupine(this.skupinaId).subscribe(
      (response) => {
        this.claniSkupine=response;
      }
    )
  }

  toClan(clanId: number){
      this.router.navigate(['/clan'], {
        queryParams: {clanId: clanId}
      })
  }

  onDeletClanSkupineSuccess(){
    this.refresh();
  }
}
