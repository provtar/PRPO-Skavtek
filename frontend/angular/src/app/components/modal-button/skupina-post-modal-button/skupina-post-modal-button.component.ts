import { Component, Input, ViewChild } from '@angular/core';
import { SkupinaPostModalComponent } from '../../modal/skupina-post-modal/skupina-post-modal.component';

@Component({
  selector: 'app-skupina-post-modal-button',
  standalone: true,
  imports: [SkupinaPostModalComponent],
  templateUrl: './skupina-post-modal-button.component.html',
  styleUrl: './skupina-post-modal-button.component.css'
})
export class SkupinaPostModalButtonComponent {
  @ViewChild('skupinaPostModal') skupinaPostModal : SkupinaPostModalComponent | undefined;

  openModal(){
    this.skupinaPostModal?.open();
  }
}
