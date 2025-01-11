import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SkupinaService } from '../../../services/skupina.service';
import { EventManager } from '@angular/platform-browser';

@Component({
  selector: 'app-clan-skupine-delete-one-button',
  standalone: true,
  imports: [],
  templateUrl: './clan-skupine-delete-one-button.component.html',
  styleUrl: './clan-skupine-delete-one-button.component.css'
})
export class ClanSkupineDeleteOneButtonComponent {

  constructor(private skupinaService: SkupinaService) {}

  @Input() skupinaId!: number;
  @Input() clanId!: number;
  @Output() deleteClanSuccess = new EventEmitter<void>();

  public odstraniClanaSkupine(){
    if(this.skupinaId && this.clanId){
      this.skupinaService.deleteEnegaClanaSkupine(this.skupinaId, this.clanId).subscribe(
        (response) => {
          this.deleteClanSuccess.emit();
        }
      )
    }
  }
}
