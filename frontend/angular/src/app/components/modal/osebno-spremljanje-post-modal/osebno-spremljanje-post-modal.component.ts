import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { OsebnoSpremljanje, Srecanje } from '../../../services/data/user-data.service';
import { GlobalVarService } from '../../../services/data/global-var.service';
import { SrecanjePostFormComponent } from "../../form/srecanje-post-form/srecanje-post-form.component";
import { OsebnoSpremljanjePostFormComponent } from "../../form/osebno-spremljanje-post-form/osebno-spremljanje-post-form.component";

@Component({
  selector: 'app-osebno-spremljanje-post-modal',
  standalone: true,
  imports: [OsebnoSpremljanjePostFormComponent, CommonModule],
  templateUrl: './osebno-spremljanje-post-modal.component.html',
  styleUrl: './osebno-spremljanje-post-modal.component.css'
})
export class OsebnoSpremljanjePostModalComponent {
  @Input() clanId! : number;
  @Output() postSuccess = new EventEmitter<OsebnoSpremljanje>();
  @Output() closeModal = new EventEmitter<void>();

  constructor(private globalVar : GlobalVarService) {}
  isVisible : boolean = false;

  onFormSuccess(novOS : OsebnoSpremljanje){
    this.postSuccess.emit(novOS);
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
