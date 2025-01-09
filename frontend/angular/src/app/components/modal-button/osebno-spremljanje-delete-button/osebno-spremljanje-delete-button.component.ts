import { Component, EventEmitter, Input, Output } from '@angular/core';
import { OsebnoSpremljanjeServiceService } from '../../../services/osebno-spremljanje-service.service';

@Component({
  selector: 'app-osebno-spremljanje-delete-button',
  standalone: true,
  imports: [],
  templateUrl: './osebno-spremljanje-delete-button.component.html',
  styleUrl: './osebno-spremljanje-delete-button.component.css'
})
export class OsebnoSpremljanjeDeleteButtonComponent {
  @Input() osebnoSpremljanjeId!: number;
  @Output() deleteSuccess = new EventEmitter<void>();

  constructor(private osService : OsebnoSpremljanjeServiceService){}

  zbrisiOS(){
    this.osService.deleteOsebnoSpremljanje(this.osebnoSpremljanjeId).subscribe(
      (res) => {
        this.deleteSuccess.emit();
      }
    )
  }
}
