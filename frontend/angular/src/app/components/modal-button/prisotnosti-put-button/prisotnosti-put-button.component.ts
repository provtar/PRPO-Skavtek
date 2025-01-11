import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { Prisotnost } from '../../../services/data/user-data.service';
import { PrisotnostiPutModalComponent } from '../../modal/prisotnosti-put-modal/prisotnosti-put-modal.component';

@Component({
  selector: 'app-prisotnosti-put-button',
  standalone: true,
  imports: [PrisotnostiPutModalComponent],
  templateUrl: './prisotnosti-put-button.component.html',
  styleUrl: './prisotnosti-put-button.component.css'
})
export class PrisotnostiPutButtonComponent {
  @Input() prisotnosti! : Prisotnost[];
  @Output() putSuccess = new EventEmitter<Prisotnost[]>();

  @ViewChild('prisotnostiPutModal') prisotnostiPutModal : PrisotnostiPutModalComponent | undefined;

  openModal(){
    this.prisotnostiPutModal?.open();
  }

  onPutSuccess(posodobljenePrisotnosti : Prisotnost[]){
    this.putSuccess.emit(posodobljenePrisotnosti);
  }
}
