import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { SkupinaPostModalComponent } from '../../modal/skupina-post-modal/skupina-post-modal.component';
import { Skupina } from '../../../services/data/user-data.service';

@Component({
  selector: 'app-skupina-post-modal-button',
  standalone: true,
  imports: [SkupinaPostModalComponent],
  templateUrl: './skupina-post-modal-button.component.html',
  styleUrl: './skupina-post-modal-button.component.css'
})
export class SkupinaPostModalButtonComponent {
  @ViewChild('skupinaPostModal') skupinaPostModal : SkupinaPostModalComponent | undefined;
  @Output() postSuccess = new EventEmitter<Skupina>();
  @Output() modalClosed = new EventEmitter<void>()

  openModal(){
    this.skupinaPostModal?.open();
  }

  onPostSuccess(novaSkupina : Skupina){
    this.postSuccess.emit(novaSkupina);
  }

  onModalClose(){
    this.modalClosed.emit();
  }
}
