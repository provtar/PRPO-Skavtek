import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Clan, UserDataService } from '../../../services/data/user-data.service';
import { ClanDataService, ClanPutData } from '../../../services/clan.service';
import { GlobalVarService } from '../../../services/data/global-var.service';
import { ClanPutFormComponent } from "../../form/clan-put-form/clan-put-form.component";

@Component({
  selector: 'app-clan-put-modal',
  standalone: true,
  imports: [CommonModule, ClanPutFormComponent],
  templateUrl: './clan-put-modal.component.html',
  styleUrl: './clan-put-modal.component.css'
})
export class ClanPutModalComponent {
  @Input() clan! : Clan;
  @Output() putSuccess = new EventEmitter<Clan>();
  @Output() closeModal = new EventEmitter<void>();

  constructor(private globalVar : GlobalVarService) {}
  isVisible = false;

  onPutSuccess(posodobljenClan : Clan){
    this.putSuccess.emit(posodobljenClan);
    setTimeout(() => {
      this.close();
    }, this.globalVar.modalPutFadeTime);
  }

  open() {
    this.isVisible = true;
  }

  close() {
    this.isVisible = false;
    this.closeModal.emit();
  }
}
