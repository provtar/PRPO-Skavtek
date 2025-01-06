import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { Skupina, Srecanje } from '../../../services/data/user-data.service';
import { outputToObservable } from '@angular/core/rxjs-interop';
import { SrecanjePostModalComponent } from '../../modal/srecanje-post-modal/srecanje-post-modal.component';

@Component({
  selector: 'app-srecanje-post-button',
  standalone: true,
  imports: [SrecanjePostModalComponent],
  templateUrl: './srecanje-post-button.component.html',
  styleUrl: './srecanje-post-button.component.css'
})
export class SrecanjePostButtonComponent {
  @Input() skupinaId! : number;
  @Output() putSuccess = new EventEmitter<Srecanje>();

  @ViewChild('srecanjePostModal') srecanjePostModal : SrecanjePostModalComponent  | undefined;

  openModal(){
    this.srecanjePostModal?.open();
  }

  onPostSuccess(novoSrecanje : Srecanje){
    this.putSuccess.emit(novoSrecanje);
  }
}
