import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { Srecanje } from '../../../services/data/user-data.service';
import { SrecanjePutModalComponent } from '../../modal/srecanje-put-modal/srecanje-put-modal.component';

@Component({
  selector: 'app-srecanje-put-button',
  standalone: true,
  imports: [SrecanjePutModalComponent],
  templateUrl: './srecanje-put-button.component.html',
  styleUrl: './srecanje-put-button.component.css'
})
export class SrecanjePutButtonComponent {
  @Input() srecanje! : Srecanje;
  @Output() putSuccess = new EventEmitter<Srecanje>();

  @ViewChild('srecanjePutModal') srecanjePutModal : SrecanjePutModalComponent| undefined;

  openModal(){
    this.srecanjePutModal?.open();
  }

  onPutSuccess(posodobljenoSrecanje : Srecanje){
    this.putSuccess.emit(posodobljenoSrecanje);
  }
}
