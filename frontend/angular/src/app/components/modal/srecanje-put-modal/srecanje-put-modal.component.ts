import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Srecanje } from '../../../services/data/user-data.service';
import { GlobalVarService } from '../../../services/data/global-var.service';
import { CommonModule } from '@angular/common';
import { SrecanjePutFormComponent } from "../../form/srecanje-put-form/srecanje-put-form.component";

@Component({
  selector: 'app-srecanje-put-modal',
  standalone: true,
  imports: [CommonModule, SrecanjePutFormComponent],
  templateUrl: './srecanje-put-modal.component.html',
  styleUrl: './srecanje-put-modal.component.css'
})
export class SrecanjePutModalComponent {
  @Input() srecanje!: Srecanje;
  @Output() putSuccess = new EventEmitter<Srecanje>();
  @Output() closeModal = new EventEmitter<void>();

  constructor(private globalVar : GlobalVarService) {}
  isVisible : boolean = false;

  onFormSuccess(posodobljenoSrecanje : Srecanje){
    this.putSuccess.emit(posodobljenoSrecanje);
    setTimeout(() => {
      this.close();
    }, this.globalVar.modalPutFadeTime);
  }

  open() {
    this.isVisible = true;
  }

  close(){
    this.isVisible = false;
    this.closeModal.emit();
  }

}
