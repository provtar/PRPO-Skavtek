import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { OsebnoSpremljanje } from '../../../services/data/user-data.service';
import { OsebnoSpremljanjePutModalComponent } from '../../modal/osebno-spremljanje-put-modal/osebno-spremljanje-put-modal.component';

@Component({
  selector: 'app-osebno-spremljanje-put-button',
  standalone: true,
  imports: [OsebnoSpremljanjePutModalComponent],
  templateUrl: './osebno-spremljanje-put-button.component.html',
  styleUrl: './osebno-spremljanje-put-button.component.css'
})
export class OsebnoSpremljanjePutButtonComponent {
  @Input() osebnoSpremljanje! : OsebnoSpremljanje;
  @Output() putSuccess = new EventEmitter<OsebnoSpremljanje>();

  @ViewChild('OSputModal') OSputModal : OsebnoSpremljanjePutModalComponent| undefined;

  openModal(){
    this.OSputModal?.open();
  }

  onPostSuccess(posodobljenoOS : OsebnoSpremljanje){
    this.putSuccess.emit(posodobljenoOS);
  }
}
