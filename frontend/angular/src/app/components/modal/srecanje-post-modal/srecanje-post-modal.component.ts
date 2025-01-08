import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Srecanje } from '../../../services/data/user-data.service';
import { GlobalVarService } from '../../../services/data/global-var.service';
import { SrecanjePostFormComponent } from "../../form/srecanje-post-form/srecanje-post-form.component";

@Component({
  selector: 'app-srecanje-post-modal',
  standalone: true,
  imports: [CommonModule, SrecanjePostFormComponent],
  templateUrl: './srecanje-post-modal.component.html',
  styleUrl: './srecanje-post-modal.component.css'
})
export class SrecanjePostModalComponent {
  @Input() skupinaId! : number;
  @Output() postSuccess = new EventEmitter<Srecanje>();
  @Output() closeModal = new EventEmitter<void>();

  constructor(private globalVar : GlobalVarService) {}
  isVisible : boolean = false;

  onFormSuccess(novoSrecanje : Srecanje){
    this.postSuccess.emit(novoSrecanje);
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
