import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PrisotnostiService } from '../../../services/prisotnosti.service';

@Component({
  selector: 'app-prisotnosti-delete-vse-button',
  standalone: true,
  imports: [],
  templateUrl: './prisotnosti-delete-vse-button.component.html',
  styleUrl: './prisotnosti-delete-vse-button.component.css'
})
export class PrisotnostiDeleteVseButtonComponent {
  @Input() srecanjeId! : number;
  @Output() deleteSuccess = new EventEmitter<void>();

  constructor(private prisotnostiService : PrisotnostiService) {}

  zbrisiPrisotnosti(){
    this.prisotnostiService.deleteVsePrisotnosti(this.srecanjeId).subscribe(
      (response) => {
        this.deleteSuccess.emit();
      }
    );
  }
}
