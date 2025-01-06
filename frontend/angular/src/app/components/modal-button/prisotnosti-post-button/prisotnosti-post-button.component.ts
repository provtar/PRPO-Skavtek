import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Prisotnost } from '../../../services/data/user-data.service';
import { PrisotnostiService } from '../../../services/prisotnosti.service';

@Component({
  selector: 'app-prisotnosti-post-button',
  standalone: true,
  imports: [],
  templateUrl: './prisotnosti-post-button.component.html',
  styleUrl: './prisotnosti-post-button.component.css'
})
export class PrisotnostiPostButtonComponent {
  constructor(private prisotnostiService : PrisotnostiService) {}

  @Input() srecanjeId!: number;
  @Output() postSuccess = new EventEmitter<Prisotnost[]>();

  postPrisotnosti(){
    this.prisotnostiService.postPrisotnosti(this.srecanjeId).subscribe(
      (response) => {
        this.postSuccess.emit(response);
      }
    );
  }
}
