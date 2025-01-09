import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { OsebnoSpremljanje } from '../../../services/data/user-data.service';
import { OsebnoSpremljanjePostModalComponent } from '../../modal/osebno-spremljanje-post-modal/osebno-spremljanje-post-modal.component';

@Component({
  selector: 'app-osebno-spremljanje-post-button',
  standalone: true,
  imports: [OsebnoSpremljanjePostModalComponent],
  templateUrl: './osebno-spremljanje-post-button.component.html',
  styleUrl: './osebno-spremljanje-post-button.component.css'
})
export class OsebnoSpremljanjePostButtonComponent {
  @Input() clanId!: number;
  @Output() postSuccess = new EventEmitter<OsebnoSpremljanje>();

  @ViewChild('OSpostModal') OSpostModal : OsebnoSpremljanjePostModalComponent| undefined;

  openModal(){
    this.OSpostModal?.open();
  }

  onPostSuccess(novOS : OsebnoSpremljanje){
    this.postSuccess.emit(novOS);
  }
}
