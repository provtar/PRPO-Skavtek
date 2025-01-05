import { Component, Input, ViewChild, viewChild, ViewContainerRef } from '@angular/core';
import { Skupina, UserDataService } from '../../../services/data/user-data.service';
import { SkupinaService } from '../../../services/skupina.service';
import { SkupinaPutModalComponent } from '../../modal/skupina-put-modal/skupina-put-modal.component';

@Component({
  selector: 'app-skupina-put-modal-button',
  standalone: true,
  imports: [SkupinaPutModalComponent],
  templateUrl: './skupina-put-modal-button.component.html',
  styleUrl: './skupina-put-modal-button.component.css'
})
export class SkupinaPutModalButtonComponent {
  constructor() {}
  @ViewChild('skupinaPutModal') skupinaPutModal!: SkupinaPutModalComponent;

  @Input() skupina!: Skupina;

  urediSkupino(){
    // console.log(this.skupina)
    this.skupinaPutModal.open();
    // Za dinamicno resevanje komponent, za nas je dovolj *ngIf
    // this.putModal.clear();
    // const modalRef = this.putModal.createComponent(SkupinaPutModalComponent);
    // modalRef.instance.skupina = this.skupina;
  }
  atClose(){
    console.log(this.skupinaPutModal.posodobljenaSkupina);
  }

}
