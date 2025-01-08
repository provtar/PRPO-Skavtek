import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SrecanjaService } from '../../../services/srecanja.service';

@Component({
  selector: 'app-srecanje-delete-button',
  standalone: true,
  imports: [],
  templateUrl: './srecanje-delete-button.component.html',
  styleUrl: './srecanje-delete-button.component.css'
})
export class SrecanjeDeleteButtonComponent {
  @Input() srecanjeId! : number;
  @Output() deleteSuccess = new EventEmitter<void>();

  constructor(private srecanjaService : SrecanjaService) {}

  zbrisiSrecanje(){
    this.srecanjaService.deleteSrecanje(this.srecanjeId).subscribe(
      (response) => {
        this.deleteSuccess.emit();
      }
    );
  }
}
