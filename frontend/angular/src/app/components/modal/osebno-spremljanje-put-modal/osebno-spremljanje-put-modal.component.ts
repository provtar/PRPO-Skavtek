import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { OsebnoSpremljanje, Srecanje } from '../../../services/data/user-data.service';
import { GlobalVarService } from '../../../services/data/global-var.service';
import { SrecanjePostFormComponent } from "../../form/srecanje-post-form/srecanje-post-form.component";
import { OsebnoSpremljanjePostFormComponent } from "../../form/osebno-spremljanje-put-form/osebno-spremljanje-put-form.component";

@Component({
  selector: 'app-osebno-spremljanje-put-modal',
  standalone: true,
  imports: [CommonModule, OsebnoSpremljanjePostFormComponent],
  templateUrl: './osebno-spremljanje-put-modal.component.html',
  styleUrl: './osebno-spremljanje-put-modal.component.css'
})
export class OsebnoSpremljanjePutModalComponent {
  @Input() osebnoSpremljanje! : OsebnoSpremljanje;
  @Output() postSuccess = new EventEmitter<OsebnoSpremljanje>();
  @Output() closeModal = new EventEmitter<void>();

  constructor(private globalVar : GlobalVarService) {}
  isVisible : boolean = false;

  onFormSuccess(posodobljenOS : OsebnoSpremljanje){
    this.postSuccess.emit(posodobljenOS);
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
